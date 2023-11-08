package knu.MoApp.Controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import knu.MoApp.Service.UserService
import knu.MoApp.data.Dto.Auth.Req.AuthLoginReq
import knu.MoApp.data.Dto.Auth.Res.AuthLoginRes
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
@Api(description = "사용자 인증 관련 API")
class AuthController(val userService: UserService) {

    @GetMapping("/login")
    @ApiOperation(
        value = "카카오 로그인 코드를 사용하여 AccessToken을 받아옵니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "로그인 성공"),
        ApiResponse(code = 401, message = "유저가 아닙니다"),
        ApiResponse(code = 500, message = "카카오 코드 오류")
    )
    fun login(code : String): ResponseEntity<AuthLoginRes?>{
        return userService.login(code)
    }

    @PostMapping("/login")
    @ApiOperation(
        value = "AccessToken을 이용하여 사용자 정보와 AccessToken을 받아옵니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "로그인 성공"),
        ApiResponse(code = 401, message = "유저가 아닙니다")
    )
    fun login(authLoginReq: AuthLoginReq): ResponseEntity<AuthLoginRes?>{
        return userService.login(authLoginReq)
    }


}