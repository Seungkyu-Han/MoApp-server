package knu.MoApp.data.Entity

import knu.MoApp.data.Enum.GroupScheduleStatusEnum
import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "group_schedule")
data class GroupSchedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @OneToOne
    val group: Group,

    var startTime: Int,
    var endTime: Int,
    var date: Date,

    @Enumerated(EnumType.ORDINAL)
    var groupScheduleStatusEnum: GroupScheduleStatusEnum
)
