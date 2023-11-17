package knu.MoApp.data.Entity.Embedded

import knu.MoApp.data.Entity.Group
import knu.MoApp.data.Entity.User
import java.io.Serializable
import javax.persistence.Embeddable
import javax.persistence.ManyToOne

@Embeddable
data class GroupUserRelation (
    @ManyToOne
    var user: User,

    @ManyToOne
    var group: Group
): Serializable