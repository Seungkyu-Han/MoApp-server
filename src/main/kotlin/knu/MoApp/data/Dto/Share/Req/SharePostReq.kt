package knu.MoApp.data.Dto.Share.Req

import java.time.LocalDate

data class SharePostReq(
    val name: String,
    val userIdList: List<Int>,
    val startDate: LocalDate,
    val endDate: LocalDate
)
