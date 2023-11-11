package knu.MoApp.Repository

import knu.MoApp.data.Entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository:JpaRepository<User, Int> {

    fun findByAccessToken(accessToken: String): User?
    fun findByName(name: String): User?
    fun existsUserByName(name: String): Boolean
}