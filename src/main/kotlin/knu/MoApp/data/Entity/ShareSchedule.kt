package knu.MoApp.data.Entity

import knu.MoApp.data.Enum.ShareScheduleStatusEnum
import java.util.Date
import javax.persistence.*

@Entity
@Table(name = "share_schedule")
data class ShareSchedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @OneToOne
    val share: Share,

    var startTime: Int,
    var endTime: Int,
    var date: Date,

    @Enumerated(EnumType.ORDINAL)
    var shareScheduleStatusEnum: ShareScheduleStatusEnum
)
