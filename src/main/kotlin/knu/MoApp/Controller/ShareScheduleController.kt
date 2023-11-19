package knu.MoApp.Controller

import io.swagger.annotations.*
import knu.MoApp.Service.ShareScheduleService
import knu.MoApp.data.Dto.ShareSchedule.Req.ShareSchedulePostReq
import knu.MoApp.data.Dto.ShareSchedule.ShareScheduleActiveRes
import knu.MoApp.data.Enum.ShareScheduleStatusEnum
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
    @ApiImplicitParams(
        ApiImplicitParam(name = "id", value = "조회하려는 그룹의 id", dataTypeClass = Int::class, paramType = "query")
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

    @DeleteMapping("/schedule")
    @ApiOperation(
        value = "해당 그룹에 생긴 미팅을 삭제합니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "삭제 성공")
    )
    @ApiImplicitParams(
        ApiImplicitParam(name = "id", value = "Active 상태를 해제하고 싶은 그룹의 id", dataTypeClass = Int::class, paramType = "query")
    )
    fun deleteSchedule(id: Int, @ApiIgnore authentication: Authentication): ResponseEntity<HttpStatus>{
        return shareScheduleService.deleteSchedule(id, authentication)
    }

    @PutMapping("/schedule-req")
    @ApiOperation(
        value = "미팅 요청에 대한 본인의 상태를 작성합니다.",
        notes = "한명이라도 false를 입력할 시에 해당 요청은 broken 상태로 변경되어 요청이 내려가게 됩니다.\n" +
                "모든 유저가 true를 입력할 시에 해당 요청은 active 상태로 변경 됩니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "파괴 성공"),
        ApiResponse(code = 201, message = "작성 성공")
    )
    @ApiImplicitParams(
        ApiImplicitParam(name = "id", value = "공유방의 Id", dataTypeClass = Int::class, paramType = "query"),
        ApiImplicitParam(name = "available", value = "본인의 상태", dataTypeClass = Boolean::class, paramType = "query")
    )
    fun scheduleReq(id: Int, available: Boolean, @ApiIgnore authentication: Authentication): ResponseEntity<HttpStatus>{
        return shareScheduleService.scheduleReq(id, available, authentication)
    }


    @GetMapping("/state")
    @ApiOperation(
        value = "해당 그룹의 스케줄 상태를 가져옵니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "조회 성공")
    )
    fun state(id: Int, @ApiIgnore authentication: Authentication):ResponseEntity<ShareScheduleStatusEnum>{
        return shareScheduleService.state(id, authentication)
    }

    @GetMapping("/active")
    @ApiOperation(
        value = "해당 그룹의 스케줄이 Active 상태라면, 해당 Active 상태의 스케줄 정보를 가져옵니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "조회 성공"),
        ApiResponse(code = 404, message = "Active 상태가 아니거나 해당 공유방이 없습니다."),
        ApiResponse(code = 500, message = "알 수 없는 오류")
    )
    @ApiImplicitParams(
        ApiImplicitParam(name = "id", value = "공유방의 id", dataTypeClass = Int::class, paramType = "query")
    )
    fun active(id: Int, @ApiIgnore authentication: Authentication): ResponseEntity<ShareScheduleActiveRes>{
        return shareScheduleService.active(id, authentication)
    }
}