package knu.MoApp.data.Dto.User.Req

import io.swagger.annotations.ApiModelProperty

data class UserInfoReq(
    @ApiModelProperty(value = "변경하려고 하는 이름")
    val name:String)
