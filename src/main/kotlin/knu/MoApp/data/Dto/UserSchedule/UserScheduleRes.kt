package knu.MoApp.data.Dto.UserSchedule

data class UserScheduleRes(
    val scheduleEvents: MutableList<UserScheduleResElement> = mutableListOf()
)