package knu.MoApp.Controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import knu.MoApp.Service.ShareScheduleService
import knu.MoApp.data.Dto.ShareSchedule.Req.ShareSchedulePostReq
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/api/share-schedule")
@Api(description = "공유 스케줄 관련 API")
class ShareScheduleController(private val shareScheduleService: ShareScheduleService) {

    @GetMapping("/schedule")
    @ApiOperation(
        value = "해당 그룹의 불가능 시간을 조회합니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "조회 성공")
    )
    fun schedule(id: Int, @ApiIgnore authentication: Authentication):ResponseEntity<Array<BooleanArray>>{
        return shareScheduleService.schedule(id, authentication)
    }

    @PostMapping("/schedule")
    @ApiOperation(
        value = "해당 그룹에 미팅을 요청합니다."
    )
    @ApiResponses(
        ApiResponse(code = 201, message = "생성 성공")
    )
    fun schedule(@RequestBody shareSchedulePostReq: ShareSchedulePostReq, @ApiIgnore authentication: Authentication): ResponseEntity<HttpStatus>{
        return shareScheduleService.schedule(shareSchedulePostReq, authentication)
    }

}