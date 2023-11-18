package knu.MoApp.Repository

import knu.MoApp.data.Entity.Share
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ShareRepository:JpaRepository<Share, Int> {

}