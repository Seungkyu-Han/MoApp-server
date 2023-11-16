package knu.MoApp.data.Dto.User.Res

import knu.MoApp.data.Entity.User

data class UserInfoRes(
    val id: Int,
    val name: String
){
    constructor(user: User): this(id = user.id, name = user.name)
}