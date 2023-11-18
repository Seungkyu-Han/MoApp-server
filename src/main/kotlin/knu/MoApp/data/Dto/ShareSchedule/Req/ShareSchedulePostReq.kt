package knu.MoApp.data.Dto.ShareSchedule.Req

import java.time.LocalDate

data class ShareSchedulePostReq (
    val share_id: Int,
    val startTime: Int,
    val endTime: Int,
    val date: LocalDate
)