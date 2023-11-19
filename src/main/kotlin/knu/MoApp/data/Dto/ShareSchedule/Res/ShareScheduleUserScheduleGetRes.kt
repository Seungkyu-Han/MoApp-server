package knu.MoApp.data.Dto.ShareSchedule.Res

import knu.MoApp.data.Entity.UserScheduleInShare
import java.time.LocalDate

data class ShareScheduleUserScheduleGetRes(
    val startTime: Int,
    val endTime: Int,
    val date: LocalDate
){
    constructor(shareScheduleInShare: UserScheduleInShare): this(
        startTime = shareScheduleInShare.startTime, endTime = shareScheduleInShare.endTime, date = shareScheduleInShare.date
    )
}
