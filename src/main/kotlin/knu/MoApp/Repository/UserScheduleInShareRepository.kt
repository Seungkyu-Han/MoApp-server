package knu.MoApp.Repository

import knu.MoApp.data.Entity.ShareUser
import knu.MoApp.data.Entity.UserScheduleInShare
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface UserScheduleInShareRepository:JpaRepository<UserScheduleInShare, Int> {

    fun findByShareUserAndAndDate(shareUser: ShareUser, date: LocalDate): List<UserScheduleInShare>
}