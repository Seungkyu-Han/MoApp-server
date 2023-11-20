package knu.MoApp.Service

import knu.MoApp.data.Enum.DayEnum
import knu.MoApp.data.Dto.UserSchedule.UserScheduleRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
interface UserScheduleService {
    fun schedule(authentication: Authentication): ResponseEntity<UserScheduleRes?>

    fun schedule(startTime: Int, endTime: Int, day: DayEnum, scheduleName: String, authentication: Authentication): ResponseEntity<HttpStatus>

    fun schedule(id: Int, startTime: Int, endTime: Int, day: DayEnum, scheduleName: String, authentication: Authentication): ResponseEntity<HttpStatus>

    fun patchSchedule(id: Int, startTime: Int?, endTime: Int?, day: DayEnum?, scheduleName: String?, authentication: Authentication): ResponseEntity<HttpStatus>

    fun schedule(id:Int, authentication: Authentication): ResponseEntity<HttpStatus>

    fun shareSchedule(authentication: Authentication): ResponseEntity<UserScheduleRes>
}