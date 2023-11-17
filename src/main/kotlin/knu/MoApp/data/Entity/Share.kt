package knu.MoApp.data.Entity

import lombok.Data
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "share")
@Data
data class Share(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int?,

    @Column(length = 20)
    var name: String,

    var startDate: LocalDate,
    var endDate: LocalDate
)
