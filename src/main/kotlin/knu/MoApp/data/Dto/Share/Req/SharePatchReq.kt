package knu.MoApp.data.Dto.Share.Req

import java.time.LocalDate

data class SharePatchReq(
    val id: Int,
    val name: String?,
    val startDate: LocalDate?,
    val endDate: LocalDate?
)