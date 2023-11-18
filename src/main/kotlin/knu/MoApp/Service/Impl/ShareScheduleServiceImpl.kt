package knu.MoApp.Service.Impl

import knu.MoApp.Repository.*
import knu.MoApp.Service.ShareScheduleService
import knu.MoApp.Util.getDayEnumFromDate
import knu.MoApp.Util.getDayEnumList
import knu.MoApp.data.Entity.Embedded.ShareUserRelation
import knu.MoApp.data.Enum.ShareScheduleStatusEnum
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.time.temporal.ChronoUnit

@Service
class ShareScheduleServiceImpl(
    private val shareScheduleRepository: ShareScheduleRepository,
    private val shareUserRepository: ShareUserRepository,
    private val shareRepository: ShareRepository,
    private val userScheduleRepository: UserScheduleRepository,
    private val userScheduleInShareRepository: UserScheduleInShareRepository
):ShareScheduleService {

    override fun schedule(id: Int, authentication: Authentication):ResponseEntity<Array<BooleanArray>> {
        val share = shareRepository.findById(id)

        if(share.isEmpty)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        val shareUserList = shareUserRepository.findUserByShare(share.get())

        val dayDiff = ChronoUnit.DAYS.between(share.get().endDate, share.get().startDate)

        val dayOfWeek = getDayEnumList(getDayEnumFromDate(share.get().startDate), getDayEnumFromDate(share.get().endDate))

        val result = Array((dayDiff+1).toInt()){BooleanArray(24){true} }

        for(shareUser in shareUserList){
            //유저 개인 스케줄은 불가능
            for(i in 0 until dayOfWeek.size){
                for(schedule in userScheduleRepository.findByUserAndDay(shareUser, dayOfWeek[i])){
                    for(time in schedule.startTime..schedule.endTime)
                        result[i][time] = false
                }
            }
            //유저 공유 스케줄은 불가능
            for(i in 0..dayDiff){
                for(shareSchedule in shareScheduleRepository.findFixShareScheduleByUserAndDate(shareUser, ShareScheduleStatusEnum.Active, share.get().startDate.plusDays(i))){
                    for(time in shareSchedule.startTime..shareSchedule.endTime)
                        result[i.toInt()][time] = false
                }
            }
            //유저 공유방 개인 스케줄은 불가능
            for(i in 0..dayDiff){
                for(schedule in userScheduleInShareRepository.findByShareUserAndAndDate(shareUserRepository.findById(ShareUserRelation(user = shareUser, share = share.get())).get(),
                share.get().startDate.plusDays(i))){
                    for(time in schedule.startTime..schedule.endTime)
                        result[i.toInt()][time] = false
                }
            }
        }

        return ResponseEntity(result, HttpStatus.OK)
    }
}