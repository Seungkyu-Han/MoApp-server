package knu.MoApp.data.Dto.Friend.Res

import io.swagger.annotations.ApiModelProperty
import knu.MoApp.data.Entity.User

data class FriendGetFriendRes(
    @ApiModelProperty(
        name = "친구의 Id",
        example = "12345"
    )
    val id: Int,
    @ApiModelProperty(
        name = "친구의 이름",
        example = "박종혁"
    )
    val name: String,
    @ApiModelProperty(
        name = "친구의 프로필 이미지 url",
        example = "https://avatars.githubusercontent.com/u/114932050?v=4"
    )
    var img: String?
){
    constructor(user: User) : this(id = user.id, name = user.name, img = user.img)
}
