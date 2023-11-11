package knu.MoApp.data.Entity

import knu.MoApp.data.Entity.Embedded.FriendRelation
import lombok.Data
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name="add_friend")
@Data
data class AddFriend(
    @EmbeddedId
    var friendRelation: FriendRelation
)