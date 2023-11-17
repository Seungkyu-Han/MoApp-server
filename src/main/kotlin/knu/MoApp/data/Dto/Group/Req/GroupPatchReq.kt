package knu.MoApp.data.Dto.Group.Req

import java.time.LocalDate

data class GroupPatchReq(
    val id: Int,
    val name: String?,
    val startDate: LocalDate?,
    val endDate: LocalDate?
)