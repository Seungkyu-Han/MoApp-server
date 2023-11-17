package knu.MoApp.data.Entity

import lombok.Data
import javax.persistence.*

@Entity
@Table(name = "group")
@Data
data class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(length = 20)
    var name: String
)
