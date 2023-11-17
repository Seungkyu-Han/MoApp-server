package knu.MoApp.data.Entity

import knu.MoApp.data.Entity.Embedded.ShareUserRelation
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "share_schedule_req")
data class ShareScheduleReq(

    @EmbeddedId
    val shareUserRelation: ShareUserRelation,

    var available: Boolean
)
