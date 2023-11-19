package knu.MoApp.data.Entity

import knu.MoApp.data.Enum.DayEnum
import lombok.Data
import javax.persistence.*

@Entity
@Table(name="user_schedule")
@Data
data class UserSchedule(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @ManyToOne
    val user: User,

    var startTime: Int,

    var endTime: Int,

    @Enumerated(EnumType.STRING)
    var day: DayEnum,

    @Column(length = 20)
    var eventName: String
)