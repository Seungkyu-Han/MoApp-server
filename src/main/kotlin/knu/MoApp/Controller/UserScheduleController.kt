package knu.MoApp.Controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import knu.MoApp.Service.UserScheduleService
import knu.MoApp.data.Enum.DayEnum
import knu.MoApp.data.Dto.UserSchedule.UserScheduleRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
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
        value = "사용자 개인의 스케줄 정보를 생성합니다."
    )
    @ApiResponses(
        ApiResponse(code = 201, message = "생성 성공"),
        ApiResponse(code = 400, message = "시간 오류"),
        ApiResponse(code = 401, message = "권한 없음"),
        ApiResponse(code = 404, message = "해당 시간 불가능")
    )
    fun schedule(startTime: Int, endTime: Int, day: DayEnum, scheduleName: String, @ApiIgnore authentication: Authentication): ResponseEntity<HttpStatus> {
        return userScheduleService.schedule(startTime, endTime, day, scheduleName, authentication)
    }

    @PutMapping("/schedule")
    @ApiOperation(
        value = "사용자 개인의 스케줄 정보를 수정합니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "수정 성공"),
        ApiResponse(code = 400, message = "해당 스케줄이 없음"),
        ApiResponse(code = 403, message = "유저가 생성한 스케줄이 아님"),
        ApiResponse(code = 404, message = "해당 유저가 없음\nstartTime > endTime 에러\n해당 시간 불가능")
    )
    fun schedule(id: Int, startTime: Int, endTime: Int, day: DayEnum, scheduleName: String, @ApiIgnore authentication: Authentication): ResponseEntity<HttpStatus> {
        return userScheduleService.schedule(id, startTime, endTime, day, scheduleName, authentication)
    }

    @DeleteMapping("/schedule")
    @ApiOperation(
        value = "사용자 개인의 스케줄 정보를 삭제합니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "삭제 성공"),
        ApiResponse(code = 400, message = "해당 스케줄이 존재하지 않습니다.")
    )
    fun schedule(id: Int ,@ApiIgnore authentication: Authentication): ResponseEntity<HttpStatus> {
        return userScheduleService.schedule(id, authentication)
    }

    @GetMapping("/share-schedule")
    @ApiOperation(
        value = "사용자의 공유 스케줄 정보를 가져옵니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "조회 성공")
    )
    fun shareSchedule(@ApiIgnore authentication: Authentication): ResponseEntity<UserScheduleRes>{
        return userScheduleService.shareSchedule(authentication)
    }

}