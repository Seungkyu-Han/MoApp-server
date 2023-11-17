package knu.MoApp.data.Dto.Group.Req

import java.time.LocalDate

data class GroupPostReq(
    val name: String,
    val userIdList: List<Int>,
    val startDate: LocalDate,
    val endDate: LocalDate
)
