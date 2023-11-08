package knu.MoApp.data.Dto.Auth.Res

import io.swagger.annotations.ApiModelProperty

data class AuthLoginRes(
    @ApiModelProperty(
        value = "유저의 이름"
    )
    val name: String,

    @ApiModelProperty(
        value = "유저의 ID"
    )
    val id: Int,

    @ApiModelProperty(
        value = "AccessToken"
    )
    val accessToken: String
)