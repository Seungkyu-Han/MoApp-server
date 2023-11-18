package knu.MoApp.Repository

import knu.MoApp.data.Entity.Embedded.ShareUserRelation
import knu.MoApp.data.Entity.Share
import knu.MoApp.data.Entity.ShareUser
import knu.MoApp.data.Entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ShareUserRepository: JpaRepository<ShareUser, ShareUserRelation> {

    @Query(
        "SELECT su.shareUserRelation.share FROM ShareUser su " +
                "WHERE su.shareUserRelation.user = :user"
    )
    fun findShareByUser(user: User): List<Share>

    @Query(
        "SELECT su.shareUserRelation.user FROM ShareUser su " +
                "WHERE su.shareUserRelation.share = :share"
    )
    fun findUserByShare(share: Share): List<User>


    fun existsByShareUserRelationShare(share: Share): Boolean
}