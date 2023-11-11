package knu.MoApp.Controller

import io.swagger.annotations.*
import knu.MoApp.Service.FriendService
import knu.MoApp.data.Dto.Friend.Res.FriendRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import springfox.documentation.annotations.ApiIgnore

@RestController
@RequestMapping("/api/friend")
@Api(description = "친구 관련 API")
class FriendController(private val friendService: FriendService) {

    @GetMapping("/friend")
    @ApiOperation(
        value = "친구들의 정보를 가져옵니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "조회 성공"),
        ApiResponse(code = 401, message = "권한 없음")
    )
    fun friend(@ApiIgnore authentication: Authentication): ResponseEntity<ArrayList<FriendRes>?> {
        return friendService.friend(authentication)
    }

    @DeleteMapping("/friend")
    @ApiOperation(
        value = "친구 관계를 삭제합니다."
    )
    @ApiImplicitParams(
        ApiImplicitParam(name = "id", value = "삭제하고 싶은 친구의 id", dataType = "Integer", paramType = "query")
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "삭제 성공"),
        ApiResponse(code = 400, message = "삭제하려는 친구가 존재하지 않거나, 이미 친구관계가 아님"),
        ApiResponse(code = 401, message = "권한 없음")
    )
    fun friend(@RequestParam id: Int, @ApiIgnore authentication: Authentication): ResponseEntity<HttpStatus> {
        return friendService.friend(id, authentication)
    }


}