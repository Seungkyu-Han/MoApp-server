package knu.MoApp.data.Dto.Auth.Req

import io.swagger.annotations.ApiModelProperty

data class AuthGetLoginReq(
    @ApiModelProperty(
        value = "이날어때 앱의 AccessToken",
        example = "eyJ0eXBlIjoiYWNjZXNzIiwiYWxnIjoiSFMyNTYifQ.eyJ1c2VySWQiOi0xMTM3NDc2MDUxLCJpYXQiOjE3MDAzMjA4MzUsImV4cCI6MTcwODk2MDgzNX0.3TkRNJV7y6DR8DevMa4Vz9Hf4C-IidDF-RC8yzUp4l0"
    )
    val accessToken : String
)