package knu.MoApp.data.Entity

import knu.MoApp.data.Entity.Embedded.ShareUserRelation
import lombok.Data
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name="share_user")
@Data
data class ShareUser(
    @EmbeddedId
    val shareUserRelation: ShareUserRelation
)
