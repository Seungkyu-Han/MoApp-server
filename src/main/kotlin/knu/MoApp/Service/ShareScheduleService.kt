package knu.MoApp.Service

import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
interface ShareScheduleService {

    fun schedule(id: Int, authentication: Authentication): ResponseEntity<Array<BooleanArray>>
}