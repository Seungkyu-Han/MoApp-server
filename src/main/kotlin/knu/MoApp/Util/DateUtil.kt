package knu.MoApp.Util

import knu.MoApp.data.Enum.DayEnum
import java.time.DayOfWeek
import java.time.LocalDate

fun getDayEnumFromDate(localDate: LocalDate): DayEnum{
    return when(localDate.dayOfWeek){
        DayOfWeek.MONDAY -> DayEnum.Monday
        DayOfWeek.TUESDAY -> DayEnum.Tuesday
        DayOfWeek.WEDNESDAY -> DayEnum.Wednesday
        DayOfWeek.THURSDAY -> DayEnum.Thursday
        DayOfWeek.FRIDAY -> DayEnum.Friday
        DayOfWeek.SATURDAY -> DayEnum.Saturday
        else -> DayEnum.Sunday
    }
}