package knu.MoApp.Repository

import knu.MoApp.data.Entity.Embedded.ShareUserRelation
import knu.MoApp.data.Entity.ShareScheduleReq
import org.springframework.data.jpa.repository.JpaRepository

interface ShareScheduleReqRepository: JpaRepository<ShareScheduleReq, ShareUserRelation> {
}