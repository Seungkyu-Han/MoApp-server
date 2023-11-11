package knu.MoApp.Service

import knu.MoApp.data.Dto.Friend.Res.FriendRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
interface FriendService {
    fun getFriend(authentication: Authentication): ResponseEntity<ArrayList<FriendRes>?>
    fun addFriend(id: Int, authentication: Authentication): ResponseEntity<HttpStatus>
    fun deleteFriend(id: Int, authentication: Authentication): ResponseEntity<HttpStatus>
}