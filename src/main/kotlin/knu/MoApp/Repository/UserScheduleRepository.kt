package knu.MoApp.Repository

import knu.MoApp.data.DayEnum
import knu.MoApp.data.Entity.User
import knu.MoApp.data.Entity.UserSchedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserScheduleRepository: JpaRepository<UserSchedule, Int> {

    @Query(
        "SELECT COUNT(us) FROM UserSchedule us " +
                "WHERE us.day = :day AND us.user = :user " +
                "AND (us.startTime between :startTime and :endTime OR us.endTime between :startTime and :endTime)"
    )
    fun hasTimeConflict(startTime: Int, endTime: Int, day: DayEnum, user: User): Int

    fun findByUser(user: User): List<UserSchedule>
}