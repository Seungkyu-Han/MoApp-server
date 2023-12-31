package knu.MoApp.Service

import knu.MoApp.data.Dto.ShareSchedule.Req.ShareSchedulePostReq
import knu.MoApp.data.Dto.ShareSchedule.Req.ShareSchedulePostUserScheduleReq
import knu.MoApp.data.Dto.ShareSchedule.Res.ShareScheduleActiveRes
import knu.MoApp.data.Dto.ShareSchedule.Res.ShareScheduleUserScheduleGetRes
import knu.MoApp.data.Enum.ShareScheduleStatusEnum
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
interface ShareScheduleService {

    fun schedule(id: Int, authentication: Authentication): ResponseEntity<Array<BooleanArray>>
    fun schedule(shareSchedulePostReq: ShareSchedulePostReq, authentication: Authentication): ResponseEntity<HttpStatus>
    fun deleteSchedule(id: Int, authentication: Authentication): ResponseEntity<HttpStatus>
    fun state(id: Int, authentication: Authentication): ResponseEntity<ShareScheduleStatusEnum>
    fun scheduleReq(id: Int, available: Boolean, authentication: Authentication): ResponseEntity<HttpStatus>
    fun info(id: Int, authentication: Authentication): ResponseEntity<ShareScheduleActiveRes>
    fun getUserSchedule(id: Int, authentication: Authentication): ResponseEntity<MutableList<ShareScheduleUserScheduleGetRes>>
    fun postUserSchedule(shareSchedulePostUserScheduleReq: ShareSchedulePostUserScheduleReq, authentication: Authentication): ResponseEntity<HttpStatus>
    fun deleteUserSchedule(id: Int, authentication: Authentication): ResponseEntity<HttpStatus>
}