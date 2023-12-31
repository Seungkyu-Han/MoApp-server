package knu.MoApp.data.Dto.ShareSchedule.Res

import knu.MoApp.data.Entity.ShareSchedule
import java.time.LocalDate

data class ShareScheduleActiveRes(
    val startTime: Int,
    val endTime: Int,
    var date: LocalDate,
    var state: String
){
    constructor(shareSchedule: ShareSchedule): this(
        startTime = shareSchedule.startTime, endTime = shareSchedule.endTime ,date = shareSchedule.date, state = shareSchedule.shareScheduleStatusEnum.name)
}