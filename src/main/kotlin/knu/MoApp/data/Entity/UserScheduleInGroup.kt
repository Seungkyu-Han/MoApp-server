package knu.MoApp.data.Entity

import lombok.Data
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "user_schedule_in_group")
@Data
data class UserScheduleInGroup(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @ManyToOne
    val groupUser: GroupUser,

    var startTime: Int,
    var endTime: Int,
    var date: Date
)
