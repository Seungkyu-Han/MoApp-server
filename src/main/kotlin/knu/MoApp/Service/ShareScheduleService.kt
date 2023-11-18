package knu.MoApp.Service

import knu.MoApp.data.Dto.ShareSchedule.Req.ShareSchedulePostReq
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
interface ShareScheduleService {

    fun schedule(id: Int, authentication: Authentication): ResponseEntity<Array<BooleanArray>>

    fun schedule(shareSchedulePostReq: ShareSchedulePostReq, authentication: Authentication): ResponseEntity<HttpStatus>
}