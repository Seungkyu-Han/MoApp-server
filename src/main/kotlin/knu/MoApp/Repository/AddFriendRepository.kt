package knu.MoApp.Repository

import knu.MoApp.data.Entity.AddFriend
import knu.MoApp.data.Entity.Embedded.FriendRelation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AddFriendRepository:JpaRepository<AddFriend, FriendRelation> {
}