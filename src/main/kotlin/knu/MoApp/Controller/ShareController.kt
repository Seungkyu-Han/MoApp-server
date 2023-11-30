package knu.MoApp.Controller

import io.swagger.annotations.*
import knu.MoApp.Service.ShareService
import knu.MoApp.data.Dto.Share.Req.SharePatchReq
import knu.MoApp.data.Dto.Share.Req.SharePostReq
import knu.MoApp.data.Dto.Share.Res.ShareNearRes
import knu.MoApp.data.Dto.Share.Res.ShareRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/api/share")
@Api(description = "공유방 관련 API")
class ShareController(private val shareService: ShareService) {

    @GetMapping("/share")
    @ApiOperation(
        value = "본인이 속한 그룹들의 정보를 가져옵니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "조회 성공"),
        ApiResponse(code = 403, message = "권한 없음")
    )
    fun group(@ApiIgnore authentication: Authentication): ResponseEntity<ArrayList<ShareRes>>{
        return shareService.share(authentication)
    }

    @PostMapping("/share")
    @ApiOperation(
        value = "공유방을 생성합니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "조회 성공"),
        ApiResponse(code = 403, message = "권한 없음")
    )
    fun group(@RequestBody groupPostReq: SharePostReq, @ApiIgnore authentication: Authentication): ResponseEntity<HttpStatus>{
        return shareService.share(groupPostReq, authentication)
    }

    @PatchMapping("/share")
    @ApiOperation(
        value = "공유방 정보를 수정합니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "변경 성공"),
        ApiResponse(code = 403, message = "권한 없음")
    )
    fun group(@RequestBody groupPatchReq: SharePatchReq, @ApiIgnore authentication: Authentication): ResponseEntity<HttpStatus>{
        return shareService.share(groupPatchReq, authentication)
    }

    @DeleteMapping("/share")
    @ApiOperation(
        value = "공유방에서 탈퇴합니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "탈퇴 성공")
    )
    @ApiImplicitParams(
        ApiImplicitParam(name = "id", value = "탈퇴하려는 공유방의 ID", dataTypeClass = Int::class, paramType = "query")
    )
    fun group(id: Int, @ApiIgnore authentication: Authentication): ResponseEntity<HttpStatus>{
        return shareService.share(id, authentication)
    }

    @GetMapping("/near")
    @ApiOperation(
        value = "가장 가까운 공유 일정 정보를 가져옵니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "일정 조회 성공"),
        ApiResponse(code = 404, message = "일정 없음")
    )
    fun near(@ApiIgnore authentication: Authentication): ResponseEntity<ShareNearRes>{
        return shareService.near(authentication)
    }

    @GetMapping("info")
    @ApiOperation(
        value = "ID 이용해서 해당 공유방의 정보를 가져옵니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "정보를 조회"),
        ApiResponse(code = 403, message = "해당 유저가 없음"),
        ApiResponse(code = 404, message = "해당 공유방이 없음")
    )
    @ApiImplicitParams(
        ApiImplicitParam(name = "id", value = "조회하려는 공유방의 ID", dataTypeClass = Int::class, paramType = "query")
    )
    fun info(id: Int, @ApiIgnore authentication: Authentication): ResponseEntity<ShareRes>{
        return shareService.info(id, authentication)
    }
}