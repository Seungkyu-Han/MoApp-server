package knu.MoApp.data.Dto.Group.Res

import knu.MoApp.data.Dto.User.Res.UserInfoRes
import java.time.LocalDate

data class GroupRes(
    val id: Int,
    val name: String,
    val userInfoResList: ArrayList<UserInfoRes>,
    val startDate: LocalDate,
    val endDate: LocalDate
)