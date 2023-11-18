package knu.MoApp.data.Dto.Share.Res

import knu.MoApp.data.Dto.User.Res.UserInfoRes
import java.time.LocalDate

data class ShareRes(
    val id: Int,
    val name: String,
    val userInfoResList: ArrayList<UserInfoRes>,
    val startDate: LocalDate,
    val endDate: LocalDate
)