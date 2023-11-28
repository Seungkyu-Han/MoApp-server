package knu.MoApp.Service

import knu.MoApp.data.Dto.Auth.Req.AuthGetLoginReq
import knu.MoApp.data.Dto.Auth.Res.AuthGetLoginRes
import knu.MoApp.data.Dto.User.Req.UserInfoReq
import knu.MoApp.data.Dto.User.Res.UserInfoRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
interface UserService {
    fun login(code: String): ResponseEntity<AuthGetLoginRes>

    fun login(authLoginReq: AuthGetLoginReq): ResponseEntity<AuthGetLoginRes>
    fun loginKakaoAccess(token: String): ResponseEntity<AuthGetLoginRes>
    fun info(userInfoReq: UserInfoReq, authentication: Authentication): ResponseEntity<HttpStatus>
    fun name(name:String): ResponseEntity<Boolean>
    fun check(authentication: Authentication): ResponseEntity<HttpStatus>
    fun info(authentication: Authentication): ResponseEntity<UserInfoRes>
    fun addFriend(authentication: Authentication): ResponseEntity<Boolean>
    fun addFriend(state: Boolean, authentication: Authentication): ResponseEntity<HttpStatus>
    fun addShare(authentication: Authentication): ResponseEntity<Boolean>
    fun addShare(state: Boolean, authentication: Authentication): ResponseEntity<HttpStatus>
    fun image(multipartFile: MultipartFile, authentication: Authentication): ResponseEntity<HttpStatus>
}