package knu.MoApp.Repository

import knu.MoApp.data.Entity.Embedded.FriendRelation
import knu.MoApp.data.Entity.Friend
import knu.MoApp.data.Entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FriendRepository:JpaRepository<Friend, FriendRelation> {

    @Query("" +
            "SELECT f FROM Friend f " +
            "WHERE f.friendRelation.user1 = :user " +
            "order by f.friendRelation.user2.name")
    fun getFriendList(user: User): ArrayList<Friend>
}