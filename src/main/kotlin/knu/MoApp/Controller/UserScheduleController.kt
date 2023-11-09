package knu.MoApp.Controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import knu.MoApp.Service.UserScheduleService
import knu.MoApp.data.DayEnum
import knu.MoApp.data.Dto.UserSchedule.UserScheduleRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/api/user-schedule")
@Api(description = "사용자 스케줄 관련 API")
class UserScheduleController(private val userScheduleService: UserScheduleService) {

    @GetMapping("/schedule")
    @ApiOperation(
        value = "사용자 개인의 스케줄 정보를 가져옵니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "조회 성공"),
        ApiResponse(code = 401, message = "권한 없음")
    )
    fun schedule(@ApiIgnore authentication: Authentication): ResponseEntity<UserScheduleRes?> {
        return userScheduleService.schedule(authentication)
    }

    @PostMapping("/schedule")
    @ApiOperation(
        value = "사용자 개인의 스케줄 정보를 생성합니다.."
    )
    @ApiResponses(
        ApiResponse(code = 201, message = "생성 성공"),
        ApiResponse(code = 400, message = "시간 오류"),
        ApiResponse(code = 401, message = "권한 없음"),
        ApiResponse(code = 404, message = "해당 시간 불가능")
    )
    fun schedule(startTime: Int, endTime: Int, day: DayEnum, scheduleName: String ,@ApiIgnore authentication: Authentication): ResponseEntity<HttpStatus> {
        return userScheduleService.schedule(startTime, endTime, day, scheduleName, authentication)
    }
}