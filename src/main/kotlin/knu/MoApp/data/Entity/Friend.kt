package knu.MoApp.data.Entity

import knu.MoApp.data.Entity.Embedded.FriendRelation
import lombok.Data
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="friend")
@Data
data class Friend (
    @EmbeddedId
    var friendRelation: FriendRelation
        )