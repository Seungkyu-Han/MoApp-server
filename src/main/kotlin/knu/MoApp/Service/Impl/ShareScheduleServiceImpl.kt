package knu.MoApp.Service.Impl

import knu.MoApp.Repository.*
import knu.MoApp.Service.ShareScheduleService
import knu.MoApp.Util.getDayEnumFromDate
import knu.MoApp.Util.getDayEnumList
import knu.MoApp.data.Dto.ShareSchedule.Req.ShareSchedulePostReq
import knu.MoApp.data.Entity.Embedded.ShareUserRelation
import knu.MoApp.data.Entity.ShareScheduleReq
import knu.MoApp.data.Enum.ShareScheduleStatusEnum
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.time.temporal.ChronoUnit

@Service
class ShareScheduleServiceImpl(
    private val userRepository: UserRepository,
    private val shareScheduleRepository: ShareScheduleRepository,
    private val shareUserRepository: ShareUserRepository,
    private val shareRepository: ShareRepository,
    private val userScheduleRepository: UserScheduleRepository,
    private val userScheduleInShareRepository: UserScheduleInShareRepository,
    private val shareScheduleReqRepository: ShareScheduleReqRepository
):ShareScheduleService {

    override fun schedule(id: Int, authentication: Authentication):ResponseEntity<Array<BooleanArray>> {
        val share = shareRepository.findById(id)

        if(share.isEmpty)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        val shareUserList = shareUserRepository.findUserByShare(share.get())

        val dayDiff = ChronoUnit.DAYS.between(share.get().startDate, share.get().endDate)

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
                for(schedule in userScheduleInShareRepository.findByShareUserAndDate(shareUserRepository.findById(ShareUserRelation(user = shareUser, share = share.get())).get(),
                share.get().startDate.plusDays(i))){
                    for(time in schedule.startTime..schedule.endTime)
                        result[i.toInt()][time] = false
                }
            }
        }

        return ResponseEntity(result, HttpStatus.OK)
    }

    override fun schedule(
        shareSchedulePostReq: ShareSchedulePostReq,
        authentication: Authentication
    ): ResponseEntity<HttpStatus> {

        val user = userRepository.findById(Integer.valueOf(authentication.name))

        val share = shareRepository.findById(shareSchedulePostReq.share_id)

        if(share.isEmpty)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        val shareSchedule =  shareScheduleRepository.findByShare(share.get())
        shareSchedule.date = shareSchedulePostReq.date
        shareSchedule.startTime = shareSchedulePostReq.startTime
        shareSchedule.endTime = shareSchedulePostReq.endTime
        shareSchedule.shareScheduleStatusEnum = ShareScheduleStatusEnum.Req

        shareScheduleRepository.save(shareSchedule)

        shareScheduleReqRepository.save(ShareScheduleReq(ShareUserRelation(user.get(), share.get()), available = true))

        return ResponseEntity(HttpStatus.CREATED)
    }

    override fun state(id: Int, authentication: Authentication): ResponseEntity<ShareScheduleStatusEnum> {

        val share = shareRepository.findById(id)

        if(share.isEmpty)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        return ResponseEntity(shareScheduleRepository.findByShare(share.get()).shareScheduleStatusEnum, HttpStatus.OK)
    }

    override fun scheduleReq(id: Int, available: Boolean, authentication: Authentication): ResponseEntity<HttpStatus> {
        val user = userRepository.findById(Integer.valueOf(authentication.name))
        val share = shareRepository.findById(id)

        val shareScheduleReqList = shareScheduleReqRepository.findByShareUserRelationShare(share.get())

        if(available){
            shareScheduleReqRepository.save(ShareScheduleReq(ShareUserRelation(user.get(), share.get()), true))
            for(shareSchedule in shareScheduleReqList){
                if(!shareSchedule.available) return ResponseEntity(HttpStatus.CREATED)
            }
            val shareSchedule = shareScheduleRepository.findByShare(share.get())
            shareSchedule.shareScheduleStatusEnum = ShareScheduleStatusEnum.Active
            shareScheduleRepository.save(shareSchedule)
            return ResponseEntity(HttpStatus.CREATED)
        }
        else{
            val shareSchedule = shareScheduleRepository.findByShare(share.get())
            shareSchedule.shareScheduleStatusEnum = ShareScheduleStatusEnum.Broken
            shareScheduleRepository.save(shareSchedule)
            for(shareScheduleReq in shareScheduleReqList)
                shareScheduleReq.available = false
            shareScheduleReqRepository.saveAll(shareScheduleReqList)
            return ResponseEntity(HttpStatus.OK)
        }
    }
}