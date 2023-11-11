package knu.MoApp.data.Entity.Embedded

import knu.MoApp.data.Entity.User
import java.io.Serializable
import javax.persistence.Embeddable
import javax.persistence.ManyToOne

@Embeddable
data class FriendRelation(

    @ManyToOne
    var user1: User,

    @ManyToOne
    var user2: User
        ):Serializable