package knu.MoApp.data.Dto.UserSchedule

import knu.MoApp.data.Enum.DayEnum

data class UserScheduleResElement(
    val id: Int,
    val day: DayEnum,
    val startTime: Int,
    val endTime: Int,
    val eventName: String
)