package knu.MoApp.Repository

import knu.MoApp.data.Enum.DayEnum
import knu.MoApp.data.Entity.User
import knu.MoApp.data.Entity.UserSchedule
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserScheduleRepository: JpaRepository<UserSchedule, Int> {

    @Query(
        "SELECT us FROM UserSchedule us " +
                "LEFT JOIN us.user u " +
                "WHERE us.day = :day AND u.id = :user " +
                "AND ((us.startTime <= :startTime and :startTime <= us.endTime) OR (us.startTime <= :endTime and :endTime <= us.endTime))"
    )
    fun hasTimeConflict(startTime: Int, endTime: Int, day: DayEnum, user: Int): List<UserSchedule>


    fun findByUser(user: User): List<UserSchedule>

    fun findByUserAndDay(user: User, dayEnum: DayEnum): List<UserSchedule>
}