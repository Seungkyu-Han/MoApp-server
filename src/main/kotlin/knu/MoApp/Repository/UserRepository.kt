package knu.MoApp.Repository

import knu.MoApp.data.Entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository:JpaRepository<User, Int> {

    fun findByAccessToken(accessToken: String): User?

    @Query("SELECT u FROM User u JOIN FETCH u.scheduleEvents WHERE u.id = :id")
    fun findUserWithScheduleEvents(id: Int): User?


    fun existsUserByName(name: String): Boolean
}