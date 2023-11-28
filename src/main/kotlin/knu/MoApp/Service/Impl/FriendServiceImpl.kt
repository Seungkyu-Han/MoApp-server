package knu.MoApp.Service.Impl

import knu.MoApp.Repository.AddFriendRepository
import knu.MoApp.Repository.FriendRepository
import knu.MoApp.Repository.UserRepository
import knu.MoApp.Service.FriendService
import knu.MoApp.data.Dto.Friend.Res.FriendGetFriendRes
import knu.MoApp.data.Entity.AddFriend
import knu.MoApp.data.Entity.Embedded.FriendRelation
import knu.MoApp.data.Entity.Friend
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class FriendServiceImpl(
    private val userRepository: UserRepository,
    private val friendRepository: FriendRepository,
    private val addFriendRepository: AddFriendRepository
): FriendService {

    override fun getFriend(authentication: Authentication): ResponseEntity<ArrayList<FriendGetFriendRes>> {
        val user = userRepository.findById(Integer.valueOf(authentication.name))

        if(user.isEmpty)
            return ResponseEntity(null, HttpStatus.UNAUTHORIZED)

        val result = ArrayList<FriendGetFriendRes>()

        for(friend in friendRepository.getFriendList(user.get()))
            result.add(FriendGetFriendRes(friend.friendRelation.user2))

        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun addFriend(id: Int, authentication: Authentication): ResponseEntity<HttpStatus> {
        val user1 = userRepository.findById(Integer.valueOf(authentication.name))
        val user2 = userRepository.findById(id)

        if(user1.isEmpty)
            return ResponseEntity(null, HttpStatus.UNAUTHORIZED)

        if(user2.isEmpty)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        val friendRelation = FriendRelation(user1.get(), user2.get())

        val addFriend = addFriendRepository.findById(friendRelation)

        if(addFriend.isEmpty)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        friendRepository.save(Friend(friendRelation))
        friendRepository.save(Friend(FriendRelation(user2.get(), user1.get())))
        addFriendRepository.delete(addFriend.get())

        return ResponseEntity(HttpStatus.OK)
    }

    override fun deleteFriend(id: Int, authentication: Authentication): ResponseEntity<HttpStatus> {
        val user1 = userRepository.findById(Integer.valueOf(authentication.name))
        val user2 = userRepository.findById(id)

        if(user1.isEmpty)
            return ResponseEntity(HttpStatus.UNAUTHORIZED)

        if(user2.isEmpty)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        val friendRelation = FriendRelation(user1.get(), user2.get())
        val reverseFriendRelation = FriendRelation(user2.get(), user1.get())

        val friend = friendRepository.findById(friendRelation)
        val reverseFriend = friendRepository.findById(reverseFriendRelation)

        if(friend.isEmpty)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        friendRepository.delete(friend.get())
        friendRepository.delete(reverseFriend.get())

        return ResponseEntity(HttpStatus.OK)
    }

    override fun getAddFriend(authentication: Authentication): ResponseEntity<ArrayList<FriendGetFriendRes>> {
        val user = userRepository.findById(Integer.valueOf(authentication.name))

        if(user.isEmpty)
            return ResponseEntity(null, HttpStatus.UNAUTHORIZED)

        val result = ArrayList<FriendGetFriendRes>()

        for(addFriend in addFriendRepository.getAddFriendList(user.get()))
            result.add(FriendGetFriendRes(addFriend.friendRelation.user2))

        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun addAddFriend(name: String, authentication: Authentication): ResponseEntity<HttpStatus> {
        val user = userRepository.findById(Integer.valueOf(authentication.name))

        if(user.isEmpty)
            return ResponseEntity(null, HttpStatus.UNAUTHORIZED)

        val friend = userRepository.findByName(name) ?: return ResponseEntity(HttpStatus.NOT_FOUND)

        if(!friend.add_friend)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        val friendEntity = friendRepository.findById(FriendRelation(user.get(), friend))

        if(friendEntity.isPresent)
            return ResponseEntity(HttpStatus.CONFLICT)

        addFriendRepository.save(AddFriend(FriendRelation(friend, user.get())))

        return ResponseEntity(HttpStatus.OK)
    }

    override fun deleteAddFriend(id: Int, authentication: Authentication): ResponseEntity<HttpStatus> {
        val user1 = userRepository.findById(Integer.valueOf(authentication.name))
        val user2 = userRepository.findById(id)

        if(user1.isEmpty)
            return ResponseEntity(HttpStatus.UNAUTHORIZED)

        if(user2.isEmpty)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        val addFriend = addFriendRepository.findById(FriendRelation(user1.get(), user2.get()))

        if(addFriend.isEmpty)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        addFriendRepository.delete(addFriend.get())

        return ResponseEntity(HttpStatus.OK)
    }
}