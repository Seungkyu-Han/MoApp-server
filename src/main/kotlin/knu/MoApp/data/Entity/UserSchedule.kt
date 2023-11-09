package knu.MoApp.data.Entity

import knu.MoApp.data.DayEnum
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

    val startTime: Int,

    val endTime: Int,

    @Enumerated(EnumType.STRING)
    val day: DayEnum,

    @Column(length = 20)
    val eventName: String
)