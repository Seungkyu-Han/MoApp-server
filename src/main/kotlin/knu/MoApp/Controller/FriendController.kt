package knu.MoApp.Controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import knu.MoApp.Service.FriendService
import knu.MoApp.data.Dto.Friend.Res.FriendRes
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
    fun schedule(@ApiIgnore authentication: Authentication): ResponseEntity<ArrayList<FriendRes>?> {
        return friendService.friend(authentication)
    }
}