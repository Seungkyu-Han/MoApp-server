package knu.MoApp.data.Entity

import knu.MoApp.data.Entity.Embedded.GroupUserRelation
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "group_schedule_req")
data class GroupScheduleReq(

    @EmbeddedId
    val groupUserRelation: GroupUserRelation,

    var check: Boolean
)
