package knu.MoApp.data.Entity

import lombok.Data
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name="user_schedule")
@Data
data class UserSchedule(
    @Id
    val id: Int,

    @ManyToOne
    val user: User,

    val startTime: Int,

    val endTime: Int,

    @Column(length = 20)
    val eventName: String
)