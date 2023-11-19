package knu.MoApp.data.Dto.Auth.Res

import io.swagger.annotations.ApiModelProperty

data class AuthGetLoginRes(
    @ApiModelProperty(
        value = "유저의 이름",
        example = "박종혁"
    )
    val name: String,

    @ApiModelProperty(
        value = "유저의 ID",
        example = "112523456"
    )
    val id: Int,

    @ApiModelProperty(
        value = "이날어때 앱의 AccessToken",
        example = "eyJ0eXBlIjoiYWNjZXNzIiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWQiOi0xMTM3NDc2MDUxLCJpYXQiOjE3MDAzMjA4MzUsImV4cCI6MTcwODk2MDgzNX0.3TkRNJV7y6DR8DevMa4Vz9Hf4C-IidDF-RC8yzUp4l0"
    )
    val accessToken: String
)