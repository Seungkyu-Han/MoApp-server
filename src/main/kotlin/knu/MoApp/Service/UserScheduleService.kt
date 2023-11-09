package knu.MoApp.Service

import knu.MoApp.data.DayEnum
import knu.MoApp.data.Dto.UserSchedule.UserScheduleRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
interface UserScheduleService {
    fun schedule(authentication: Authentication): ResponseEntity<UserScheduleRes?>

    fun schedule(startTime: Int, endTime: Int, day: DayEnum, scheduleName: String, authentication: Authentication): ResponseEntity<HttpStatus>
}