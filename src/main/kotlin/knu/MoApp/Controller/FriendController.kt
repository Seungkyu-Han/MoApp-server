package knu.MoApp.Controller

import io.swagger.annotations.*
import knu.MoApp.Service.FriendService
import knu.MoApp.data.Dto.Friend.Res.FriendGetFriendRes
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
    fun getFriend(@ApiIgnore authentication: Authentication): ResponseEntity<ArrayList<FriendGetFriendRes>> {
        return friendService.getFriend(authentication)
    }

    @PostMapping("/friend")
    @ApiOperation(
        value = "친구를 추가합니다."
    )
    @ApiImplicitParams(
        ApiImplicitParam(name = "id", value = "추가하고 싶은 친구의 id", dataTypeClass = Int::class, paramType = "query")
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "조회 성공"),
        ApiResponse(code = 400, message = "없는 상대방이거나, 해당 친구요청이 없었음"),
        ApiResponse(code = 401, message = "권한 없음")
    )
    fun addFriend(@RequestParam id: Int, @ApiIgnore authentication: Authentication): ResponseEntity<HttpStatus> {
        return friendService.addFriend(id, authentication)
    }

    @DeleteMapping("/friend")
    @ApiOperation(
        value = "친구 관계를 삭제합니다."
    )
    @ApiImplicitParams(
        ApiImplicitParam(name = "id", value = "삭제하고 싶은 친구의 id", dataTypeClass = Int::class, paramType = "query")
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "삭제 성공"),
        ApiResponse(code = 400, message = "삭제하려는 친구가 존재하지 않거나, 이미 친구관계가 아님"),
        ApiResponse(code = 401, message = "권한 없음")
    )
    fun deleteFriend(@RequestParam id: Int, @ApiIgnore authentication: Authentication): ResponseEntity<HttpStatus> {
        return friendService.deleteFriend(id, authentication)
    }

    @GetMapping("/add-friend")
    @ApiOperation(
        value = "친구 요청의 정보를 가져옵니다."
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "조회 성공"),
        ApiResponse(code = 401, message = "권한 없음")
    )
    fun getAddFriend(@ApiIgnore authentication: Authentication): ResponseEntity<ArrayList<FriendGetFriendRes>> {
        return friendService.getAddFriend(authentication)
    }
    @PostMapping("/add-friend")
    @ApiOperation(
        value = "친구를 요청합니다."
    )
    @ApiImplicitParams(
        ApiImplicitParam(name = "name", value = "추가하고 싶은 친구의 name", dataTypeClass = String::class, paramType = "query")
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "요청 성공"),
        ApiResponse(code = 400, message = "친구 요청을 거절한 상대입니다."),
        ApiResponse(code = 401, message = "권한 없음"),
        ApiResponse(code = 404, message = "해당 유저가 없습니다.")
    )
    fun addAddFriend(@RequestParam name: String, @ApiIgnore authentication: Authentication): ResponseEntity<HttpStatus> {
        return friendService.addAddFriend(name, authentication)
    }
    @DeleteMapping("/add-friend")
    @ApiOperation(
        value = "친구 요청을 거절합니다."
    )
    @ApiImplicitParams(
        ApiImplicitParam(name = "id", value = "거절하고 싶은 친구의 id", dataTypeClass = Int::class, paramType = "query")
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "삭제 성공"),
        ApiResponse(code = 400, message = "삭제하려는 친구가 존재하지 않거나, 친구 신청관계가 아님"),
        ApiResponse(code = 401, message = "권한 없음")
    )
    fun deleteAddFriend(@RequestParam id: Int, @ApiIgnore authentication: Authentication): ResponseEntity<HttpStatus> {
        return friendService.deleteAddFriend(id, authentication)
    }
}