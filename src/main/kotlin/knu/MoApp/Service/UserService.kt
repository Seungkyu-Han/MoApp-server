package knu.MoApp.Service

import knu.MoApp.data.Dto.Auth.Req.AuthLoginReq
import knu.MoApp.data.Dto.Auth.Res.AuthLoginRes
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
interface UserService {
    fun login(code: String): ResponseEntity<AuthLoginRes?>

    fun login(authLoginReq: AuthLoginReq): ResponseEntity<AuthLoginRes?>
}