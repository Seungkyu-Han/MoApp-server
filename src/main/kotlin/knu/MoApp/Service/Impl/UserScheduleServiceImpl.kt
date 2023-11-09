package knu.MoApp.Service.Impl

import knu.MoApp.Repository.UserRepository
import knu.MoApp.Repository.UserScheduleRepository
import knu.MoApp.Service.UserScheduleService
import knu.MoApp.data.DayEnum
import knu.MoApp.data.Dto.UserSchedule.UserScheduleRes
import knu.MoApp.data.Dto.UserSchedule.UserScheduleResElement
import knu.MoApp.data.Entity.UserSchedule
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class UserScheduleServiceImpl(
    val userRepository: UserRepository,
    val userScheduleRepository: UserScheduleRepository,
): UserScheduleService {

    override fun schedule(authentication: Authentication): ResponseEntity<UserScheduleRes?> {
        val user = userRepository.findUserWithScheduleEvents(Integer.valueOf(authentication.name)) ?: return ResponseEntity(null, HttpStatus.UNAUTHORIZED)

        val userScheduleRes = UserScheduleRes()
        for(userSchedule in userScheduleRepository.findByUser(user))
            userScheduleRes.scheduleEvents.add(UserScheduleResElement(
                id = userSchedule.id ?: 0,
                day = userSchedule.day,
                startTime = userSchedule.startTime,
                endTime =  userSchedule.endTime,
                eventName = userSchedule.eventName
            ))

        return ResponseEntity(userScheduleRes, HttpStatus.OK)
    }

    override fun schedule(
        startTime: Int,
        endTime: Int,
        day: DayEnum,
        scheduleName: String,
        authentication: Authentication
    ): ResponseEntity<HttpStatus> {
        val user = userRepository.findById(Integer.valueOf(authentication.name))

        if(user.isEmpty)
            return ResponseEntity(HttpStatus.UNAUTHORIZED)

        if(startTime > endTime)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        if(userScheduleRepository.hasTimeConflict(startTime, endTime, day, user.get()) > 0)
            return ResponseEntity(HttpStatus.NOT_FOUND)

        val userSchedule = UserSchedule(id = null, user = user.get(), day = day,
            startTime = startTime, endTime = endTime, eventName = scheduleName)

        userScheduleRepository.save(userSchedule)

        user.get().scheduleEvents?.add(userSchedule)
        userRepository.save(user.get())
        return ResponseEntity(HttpStatus.CREATED)
    }
}