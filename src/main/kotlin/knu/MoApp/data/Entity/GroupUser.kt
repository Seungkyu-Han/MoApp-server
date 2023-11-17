package knu.MoApp.data.Entity

import knu.MoApp.data.Entity.Embedded.GroupUserRelation
import lombok.Data
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name="group_user")
@Data
data class GroupUser(
    @EmbeddedId
    val groupUserRelation: GroupUserRelation
)
