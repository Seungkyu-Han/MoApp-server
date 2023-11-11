package knu.MoApp.Service

import knu.MoApp.data.Dto.Friend.Res.FriendRes
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
interface FriendService {
    fun friend(authentication: Authentication): ResponseEntity<ArrayList<FriendRes>?>
}