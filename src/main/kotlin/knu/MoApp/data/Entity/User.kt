package knu.MoApp.data.Entity

import lombok.Data
import javax.persistence.*

@Entity
@Table(name="user")
@Data
data class User(
    @Id
    var id:Int,

    @Column(length = 10, unique = true)
    var name: String,

    var accessToken: String,

    var add_friend: Boolean,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    var scheduleEvents: MutableList<UserSchedule>?,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    var friends: MutableList<Friend>?
)