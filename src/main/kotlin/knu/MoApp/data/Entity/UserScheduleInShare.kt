package knu.MoApp.data.Entity

import lombok.Data
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "user_schedule_in_share")
@Data
data class UserScheduleInShare(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @ManyToOne
    val shareUser: ShareUser,

    var startTime: Int,
    var endTime: Int,
    var date: LocalDate
)
