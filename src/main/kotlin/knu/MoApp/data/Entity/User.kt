package knu.MoApp.data.Entity

import lombok.Data
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="user")
@Data
data class User(
    @Id
    var id:Int,

    @Column(length = 10)
    var name: String,

    var accessToken: String
)