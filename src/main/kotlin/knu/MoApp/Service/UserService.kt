package knu.MoApp.Service

import knu.MoApp.data.Dto.Auth.Req.AuthLoginReq
import knu.MoApp.data.Dto.Auth.Res.AuthLoginRes
import knu.MoApp.data.Dto.User.Req.UserInfoReq
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
interface UserService {
    fun login(code: String): ResponseEntity<AuthLoginRes?>

    fun login(authLoginReq: AuthLoginReq): ResponseEntity<AuthLoginRes?>
    fun loginKakaoAccess(token: String): ResponseEntity<AuthLoginRes?>
    fun info(userInfoReq: UserInfoReq, authentication: Authentication): ResponseEntity<HttpStatus>
    fun name(name:String): ResponseEntity<Boolean>
}