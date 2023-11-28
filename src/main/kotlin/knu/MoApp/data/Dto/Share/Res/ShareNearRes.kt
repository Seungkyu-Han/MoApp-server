package knu.MoApp.data.Dto.Share.Res

import knu.MoApp.data.Entity.ShareSchedule
import java.time.LocalDate

data class ShareNearRes(
    val id: Int?,
    val name: String,
    val date: LocalDate,
    val startTime: Int,
    val endTime: Int
){
    constructor(shareSchedule: ShareSchedule): this(
        id = shareSchedule.share.id,
        name = shareSchedule.share.name,
        date = shareSchedule.date,
        startTime = shareSchedule.startTime,
        endTime = shareSchedule.endTime
    )
}
