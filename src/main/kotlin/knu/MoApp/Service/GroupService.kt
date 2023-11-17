package knu.MoApp.Service

import knu.MoApp.data.Dto.Group.Res.GroupRes
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
interface GroupService {
    fun group(authentication: Authentication): ResponseEntity<ArrayList<GroupRes>>
}