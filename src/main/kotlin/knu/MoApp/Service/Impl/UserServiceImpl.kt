package knu.MoApp.Service.Impl

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import knu.MoApp.Config.Jwt.JwtTokenProvider
import knu.MoApp.Repository.UserRepository
import knu.MoApp.Service.UserService
import knu.MoApp.data.Dto.Auth.Req.AuthLoginReq
import knu.MoApp.data.Dto.Auth.Res.AuthLoginRes
import knu.MoApp.data.Dto.User.Req.UserInfoReq
import knu.MoApp.data.Entity.User
import knu.MoApp.data.Entity.UserSchedule
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val jwtTokenProvider: JwtTokenProvider,

    @Value("\${kakao.auth.client_id}")
    private val client_id: String,

    @Value("\${kakao.auth.redirect_uri}")
    private val redirect_uri: String,

    @Value("\${jwt.secret.key}")
    private val secretKey: String
) :UserService{

    private val reqUrl = "https://kauth.kakao.com/oauth/token"
    override fun login(code: String): ResponseEntity<AuthLoginRes?> {
        val kakaoAccessToken = getKakaoAccessToken(code)

        val element = getJsonElementByAccessToken(kakaoAccessToken)

        val id = element.asJsonObject?.get("id")?.asInt ?: 0

        val optionalUser =  userRepository.findById(element.asJsonObject?.get("id")?.asInt ?: 0)

        if(optionalUser.isEmpty)
            return register(id)

        val accessToken = jwtTokenProvider.createAccessToken(optionalUser.get().id, secretKey)
        optionalUser.get().accessToken = accessToken
        userRepository.save(optionalUser.get())

        return ResponseEntity(AuthLoginRes(name = optionalUser.get().name, id = optionalUser.get().id, accessToken = accessToken), HttpStatus.OK)
    }

    private fun register(id: Int): ResponseEntity<AuthLoginRes?> {
        val user = User(
            id = id,
            name = toHashName(id),
            accessToken = jwtTokenProvider.createAccessToken(id, secretKey),
            scheduleEvents = mutableListOf(),
            add_friend = true,
            friends = mutableListOf()
        )
        userRepository.save(user)

        return ResponseEntity(AuthLoginRes(name = user.name, id = user.id, accessToken = user.accessToken), HttpStatus.CREATED)
    }

    private fun toHashName(id: Int): String {
        val now = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formattedDatetime = now.format(formatter)

        val combinedString: String = id.toString() + formattedDatetime
        val combinedBytes = combinedString.toByteArray()

        val messageDigest = MessageDigest.getInstance("SHA-256")
        val hashBytes = messageDigest.digest(combinedBytes)

        val stringBuilder = StringBuilder()
        for (hashByte in hashBytes) stringBuilder.append(String.format("%02x", hashByte))

        return stringBuilder.substring(0, 8).uppercase(Locale.getDefault())

    }

    override fun login(authLoginReq: AuthLoginReq): ResponseEntity<AuthLoginRes?> {
        val user = userRepository.findByAccessToken(authLoginReq.accessToken)
            ?: return ResponseEntity(null, HttpStatus.UNAUTHORIZED)


        return ResponseEntity(AuthLoginRes(name = user.name, id = user.id, accessToken = authLoginReq.accessToken), HttpStatus.OK)
    }

    override fun info(userInfoReq: UserInfoReq, authentication: Authentication): ResponseEntity<HttpStatus> {
        val user = userRepository.findById(Integer.valueOf(authentication.name))

        if(user.isEmpty) return ResponseEntity(HttpStatus.UNAUTHORIZED)

        user.get().name = userInfoReq.name

        try{
            userRepository.save(user.get())
        }catch (e:Exception){
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }

        return ResponseEntity(HttpStatus.OK)
    }

    override fun name(name: String): ResponseEntity<Boolean> {
        return ResponseEntity(!userRepository.existsUserByName(name), HttpStatus.OK)
    }

    private fun getKakaoAccessToken(code: String): String {
        val accessToken:String

        val url = URL(reqUrl)
        val httpURLConnection = url.openConnection() as HttpURLConnection

        httpURLConnection.requestMethod = "POST"
        httpURLConnection.doOutput = true

        val bufferedWriter = BufferedWriter(OutputStreamWriter(httpURLConnection.outputStream))
        val stringBuilder = "grant_type=authorization_code" +
                "&client_id=" + client_id +
                "&redirect_uri=" + redirect_uri +
                "&code=" + code
        bufferedWriter.write(stringBuilder)
        bufferedWriter.flush()

        httpURLConnection.responseCode

        val element: JsonElement = getJsonElement(httpURLConnection)

        accessToken = element.asJsonObject.get("access_token").asString

        bufferedWriter.close()

        return accessToken

    }

    private fun getJsonElementByAccessToken(token : String): JsonElement {
        val reqUrl = "https://kapi.kakao.com/v2/user/me"

        val url = URL(reqUrl)
        val httpURLConnection = url.openConnection() as HttpURLConnection

        httpURLConnection.requestMethod = "POST"
        httpURLConnection.doOutput = true
        httpURLConnection.setRequestProperty("Authorization", "Bearer $token")

        return getJsonElement(httpURLConnection)
    }

    private fun getJsonElement(httpURLConnection: HttpURLConnection): JsonElement {
        val bufferedReader = BufferedReader(InputStreamReader(httpURLConnection.inputStream))
        val result = StringBuilder()

        var line: String?
        while (bufferedReader.readLine().also { line = it } != null) {
            result.append(line)
        }

        bufferedReader.close()

        val jsonString = result.toString()
        return JsonParser.parseString(jsonString)
    }
}