package knu.MoApp.Controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import knu.MoApp.Service.UserService
import knu.MoApp.data.Dto.Auth.Req.AuthGetLoginReq
import knu.MoApp.data.Dto.Auth.Res.AuthGetLoginRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/api/auth")
@Api(description = "사용자 인증 관련 API")
class AuthController(val userService: UserService) {

    @GetMapping("/login")
    @ApiOperation(
        value = "카카오 로그인 코드를 사용하여 AccessToken 받아오기\n만약 가입자가 아니라면 회원가입을 시켜줍니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "로그인 성공"),
        ApiResponse(code = 201, message = "회원가입 되었습니다."),
        ApiResponse(code = 500, message = "카카오 코드 오류")
    )
    @ApiImplicitParam(name = "code", value = "카카오 로그인 코드", dataTypeClass = String::class, paramType = "query")
    fun login(code : String): ResponseEntity<AuthGetLoginRes>{
        return userService.login(code)
    }

    @PostMapping("/login")
    @ApiOperation(
        value = "AccessToken 이용하여 사용자 정보와 AccessToken(사용한 AccessToken 그대로 가져옵니다.) 받아옵니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "로그인 성공"),
        ApiResponse(code = 401, message = "유저가 아닙니다")
    )
    fun login(@RequestBody authLoginReq: AuthGetLoginReq): ResponseEntity<AuthGetLoginRes>{
        return userService.login(authLoginReq)
    }

    @GetMapping("/login-kakaoAccess")
    @ApiOperation(
        value = "카카오 AccessToken 사용하여 AccessToken 받아오기\n" +
                "만약 가입자가 아니라면 회원가입을 시켜줍니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "로그인 성공"),
        ApiResponse(code = 201, message = "회원가입 되었습니다."),
        ApiResponse(code = 500, message = "카카오 코드 오류")
    )
    @ApiImplicitParam(name = "token", value = "카카오 AccessToken", dataTypeClass = String::class, paramType = "query")
    fun loginKakaoAccess(token : String): ResponseEntity<AuthGetLoginRes>{
        return userService.loginKakaoAccess(token)
    }

    @GetMapping("/check")
    @ApiOperation(
        value = "앱 자체의 accessToken 사용하여 회원이 맞는 지 체크합니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "회원"),
        ApiResponse(code = 401, message = "없는 회원")
    )
    fun check(@ApiIgnore authentication: Authentication): ResponseEntity<HttpStatus>{
        return userService.check(authentication)
    }

}