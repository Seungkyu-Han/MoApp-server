package knu.MoApp.data.Dto.Friend.Res

import knu.MoApp.data.Entity.User

data class FriendRes(
    val id: Int,
    val name: String,
    var img: String?
){
    constructor(user: User) : this(id = user.id, name = user.name, img = user.img)
}
