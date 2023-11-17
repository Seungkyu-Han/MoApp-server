package knu.MoApp.data.Entity.Embedded

import knu.MoApp.data.Entity.Share
import knu.MoApp.data.Entity.User
import java.io.Serializable
import javax.persistence.Embeddable
import javax.persistence.ManyToOne

@Embeddable
data class ShareUserRelation (
    @ManyToOne
    var user: User,

    @ManyToOne
    var share: Share
): Serializable