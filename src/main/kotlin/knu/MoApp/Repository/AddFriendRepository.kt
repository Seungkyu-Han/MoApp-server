package knu.MoApp.Repository

import knu.MoApp.data.Entity.AddFriend
import knu.MoApp.data.Entity.Embedded.FriendRelation
import knu.MoApp.data.Entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AddFriendRepository:JpaRepository<AddFriend, FriendRelation> {
    @Query("" +
            "SELECT af FROM AddFriend af " +
            "WHERE af.friendRelation.user1 = :user " +
            "order by af.friendRelation.user2.name")
    fun getAddFriendList(user: User): ArrayList<AddFriend>


}