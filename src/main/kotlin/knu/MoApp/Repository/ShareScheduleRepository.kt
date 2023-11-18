package knu.MoApp.Repository

import knu.MoApp.data.Entity.ShareSchedule
import knu.MoApp.data.Entity.User
import knu.MoApp.data.Enum.ShareScheduleStatusEnum
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ShareScheduleRepository: JpaRepository<ShareSchedule, Int> {

    @Query(
        "SELECT ss FROM ShareSchedule ss " +
                "INNER JOIN ss.share s " +
                "INNER JOIN ShareUser su ON su.shareUserRelation.share = s " +
                "WHERE su.shareUserRelation.user = :user AND ss.shareScheduleStatusEnum = :status"
    )
    fun findFixShareScheduleByUser(user: User, status: ShareScheduleStatusEnum): List<ShareSchedule>
}