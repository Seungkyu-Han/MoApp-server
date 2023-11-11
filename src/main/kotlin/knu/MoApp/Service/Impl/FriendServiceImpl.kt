package knu.MoApp.Service.Impl

import knu.MoApp.Repository.FriendRepository
import knu.MoApp.Repository.UserRepository
import knu.MoApp.Service.FriendService
import knu.MoApp.data.Dto.Friend.Res.FriendRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class FriendServiceImpl(
    private val userRepository: UserRepository,
    private val friendRepository: FriendRepository
): FriendService {

    override fun friend(authentication: Authentication): ResponseEntity<ArrayList<FriendRes>?> {
        val user = userRepository.findById(Integer.valueOf(authentication.name))

        if(user.isEmpty)
            return ResponseEntity(null, HttpStatus.UNAUTHORIZED)

        val result = ArrayList<FriendRes>()

        for(friend in friendRepository.getFriendList(user.get()))
            result.add(FriendRes(friend.friendRelation.user2))

        return ResponseEntity(result, HttpStatus.OK)
    }
}