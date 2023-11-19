package knu.MoApp.Service

import knu.MoApp.data.Dto.Friend.Res.FriendGetFriendRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
interface FriendService {
    fun getFriend(authentication: Authentication): ResponseEntity<ArrayList<FriendGetFriendRes>>
    fun addFriend(id: Int, authentication: Authentication): ResponseEntity<HttpStatus>
    fun deleteFriend(id: Int, authentication: Authentication): ResponseEntity<HttpStatus>
    fun getAddFriend(authentication: Authentication): ResponseEntity<ArrayList<FriendGetFriendRes>>
    fun addAddFriend(name: String, authentication: Authentication): ResponseEntity<HttpStatus>
    fun deleteAddFriend(id: Int, authentication: Authentication): ResponseEntity<HttpStatus>
}