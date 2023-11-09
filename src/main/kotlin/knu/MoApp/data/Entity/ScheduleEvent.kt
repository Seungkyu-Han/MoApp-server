package knu.MoApp.data.Entity

import lombok.Data
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name="schedule_event")
@Data
data class ScheduleEvent(
    @Id
    val id: Int,

    @ManyToOne
    val user: User,

    val startTime: Int,

    val endTime: Int,

    @Column(length = 20)
    val eventName: String
)