package knu.MoApp.Service.Impl

import knu.MoApp.Repository.*
import knu.MoApp.Service.UserScheduleService
import knu.MoApp.Util.getDayEnumFromDate
import knu.MoApp.data.Enum.DayEnum
import knu.MoApp.data.Dto.UserSchedule.UserScheduleRes
import knu.MoApp.data.Dto.UserSchedule.UserScheduleResElement
import knu.MoApp.data.Entity.UserSchedule
import knu.MoApp.data.Enum.ShareScheduleStatusEnum
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class UserScheduleServiceImpl(
    val userRepository: UserRepository,
    val userScheduleRepository: UserScheduleRepository,
    val shareScheduleRepository: ShareScheduleRepository
): UserScheduleService {

    override fun schedule(authentication: Authentication): ResponseEntity<UserScheduleRes?> {
        val user = userRepository.findById(Integer.valueOf(authentication.name))

        if(user.isEmpty)
            return ResponseEntity(null, HttpStatus.UNAUTHORIZED)

        val userScheduleRes = UserScheduleRes()
        for(userSchedule in userScheduleRepository.findByUser(user.get()))
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

        if(userScheduleRepository.hasTimeConflict(startTime, endTime, day, user.get().id).isNotEmpty())
            return ResponseEntity(HttpStatus.NOT_FOUND)

        val userSchedule = UserSchedule(id = null, user = user.get(), day = day,
            startTime = startTime, endTime = endTime, eventName = scheduleName)

        userScheduleRepository.save(userSchedule)

        userRepository.save(user.get())
        return ResponseEntity(HttpStatus.CREATED)
    }

    override fun schedule(
        id: Int,
        startTime: Int,
        endTime: Int,
        day: DayEnum,
        scheduleName: String,
        authentication: Authentication
    ): ResponseEntity<HttpStatus> {
        val user = userRepository.findById(Integer.valueOf(authentication.name))
        val userSchedule = userScheduleRepository.findById(id)

        if(userSchedule.isEmpty)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        if(user.isEmpty)
            return ResponseEntity(HttpStatus.UNAUTHORIZED)

        if(userSchedule.get().user != user.get())
            return ResponseEntity(HttpStatus.FORBIDDEN)

        if(startTime > endTime)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        val userScheduleList = userScheduleRepository.hasTimeConflict(startTime, endTime, day, user.get().id)

        if(userScheduleList.size > 1)
            return ResponseEntity(HttpStatus.CONFLICT)

        if(userScheduleList[0].id != id)
            return ResponseEntity(HttpStatus.CONFLICT)

        userSchedule.get().startTime = startTime
        userSchedule.get().endTime = endTime
        userSchedule.get().day = day
        userSchedule.get().eventName = scheduleName

        userScheduleRepository.save(userSchedule.get())

        return ResponseEntity(HttpStatus.OK)
    }

    override fun patchSchedule(
        id: Int,
        startTime: Int?,
        endTime: Int?,
        day: DayEnum?,
        scheduleName: String?,
        authentication: Authentication
    ): ResponseEntity<HttpStatus> {
        val user = userRepository.findById(Integer.valueOf(authentication.name))
        val userSchedule = userScheduleRepository.findById(id)

        if(userSchedule.isEmpty)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        if(user.isEmpty)
            return ResponseEntity(HttpStatus.UNAUTHORIZED)

        if(userSchedule.get().user != user.get())
            return ResponseEntity(HttpStatus.FORBIDDEN)

        if((startTime ?: userSchedule.get().startTime) > (endTime ?: userSchedule.get().endTime))
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        val userScheduleList = userScheduleRepository.hasTimeConflict(
            startTime ?: userSchedule.get().startTime,
            endTime ?: userSchedule.get().endTime,
            day ?: userSchedule.get().day,
                    user.get().id)

        if(userScheduleList.size > 1)
            return ResponseEntity(HttpStatus.CONFLICT)

        if(userScheduleList.isNotEmpty() && userScheduleList[0].id != id)
            return ResponseEntity(HttpStatus.CONFLICT)

        userSchedule.get().startTime = startTime ?: userSchedule.get().startTime
        userSchedule.get().endTime = endTime ?: userSchedule.get().endTime
        userSchedule.get().day = day ?: userSchedule.get().day
        userSchedule.get().eventName = scheduleName ?: userSchedule.get().eventName

        userScheduleRepository.save(userSchedule.get())

        return ResponseEntity(HttpStatus.OK)
    }

    override fun schedule(id: Int, authentication: Authentication): ResponseEntity<HttpStatus> {

        val userSchedule = userScheduleRepository.findById(id)

        if(userSchedule.isEmpty)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        userScheduleRepository.delete(userSchedule.get())

        return ResponseEntity(HttpStatus.OK)
    }

    override fun shareSchedule(authentication: Authentication): ResponseEntity<UserScheduleRes> {
        val user = userRepository.findById(Integer.valueOf(authentication.name))

        if(user.isEmpty)
            return ResponseEntity(HttpStatus.UNAUTHORIZED)

        val userScheduleRes = UserScheduleRes()

        for(shareSchedule in shareScheduleRepository.findFixShareScheduleByUser(user.get(), ShareScheduleStatusEnum.Active))
            userScheduleRes.scheduleEvents.add(UserScheduleResElement(
                id = shareSchedule.id ?: 0,
                day = getDayEnumFromDate(shareSchedule.date),
                startTime = shareSchedule.startTime,
                endTime =  shareSchedule.endTime,
                eventName = shareSchedule.share.name
            ))

        return ResponseEntity(userScheduleRes, HttpStatus.OK)
    }
}