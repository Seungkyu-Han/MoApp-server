package knu.MoApp.Controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import knu.MoApp.Service.GroupService
import knu.MoApp.data.Dto.Group.Req.GroupPatchReq
import knu.MoApp.data.Dto.Group.Req.GroupPostReq
import knu.MoApp.data.Dto.Group.Res.GroupRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/api/group")
@Api(description = "공유방 관련 API")
class GroupController(private val groupService: GroupService) {

    @GetMapping("/group")
    @ApiOperation(
        value = "본인이 속한 그룹들의 정보를 가져옵니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "조회 성공"),
        ApiResponse(code = 403, message = "권한 없음")
    )
    fun group(@ApiIgnore authentication: Authentication): ResponseEntity<ArrayList<GroupRes>>{
        return groupService.group(authentication)
    }

    @PostMapping("/group")
    @ApiOperation(
        value = "공유방을 생성합니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "조회 성공"),
        ApiResponse(code = 403, message = "권한 없음")
    )
    fun group(@RequestBody groupPostReq: GroupPostReq, @ApiIgnore authentication: Authentication): ResponseEntity<HttpStatus>{
        return groupService.group(groupPostReq, authentication)
    }

    @PatchMapping("/group")
    @ApiOperation(
        value = "공유방 정보를 수정합니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "변경 성공")
    )
    fun group(@RequestBody groupPatchReq: GroupPatchReq, @ApiIgnore authentication: Authentication): ResponseEntity<HttpStatus>{
        return groupService.group(groupPatchReq, authentication)
    }
}