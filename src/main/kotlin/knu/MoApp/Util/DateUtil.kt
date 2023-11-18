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

fun getDayEnumList(startDayEnum: DayEnum, endDayEnum: DayEnum): MutableList<DayEnum>{
    val result = mutableListOf<DayEnum>()
    val dayEnumList = DayEnum.values().toList()
    if(startDayEnum > endDayEnum){
        for(dayEnum in dayEnumList)
            if(dayEnum >= startDayEnum)
                result.add(dayEnum)
        for(dayEnum in dayEnumList)
            if(dayEnum <= endDayEnum)
                result.add(dayEnum)
    }
    else{
        for(dayEnum in dayEnumList)
            if(dayEnum in startDayEnum..endDayEnum)
                result.add(dayEnum)
    }
    return result
}