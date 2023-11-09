package knu.MoApp.Controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import knu.MoApp.Service.UserService
import knu.MoApp.data.Dto.Auth.Res.AuthLoginRes
import knu.MoApp.data.Dto.User.Req.UserInfoReq
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
@Api(description = "사용자 관련 API")
class UserController(val userService: UserService) {

    @PatchMapping("/info")
    @ApiOperation(
        value = "사용자의 정보를 수정해줍니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "수정 성공"),
        ApiResponse(code = 400, message = "중복되는 이름입니다."),
        ApiResponse(code = 401, message = "유저가 아닙니다."),
        ApiResponse(code = 500, message = "토큰 오류")
    )
    fun info(userInfoReq: UserInfoReq, authentication: Authentication): ResponseEntity<HttpStatus> {
        return userService.info(userInfoReq, authentication)
    }




}