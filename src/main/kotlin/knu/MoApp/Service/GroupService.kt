package knu.MoApp.Service

import knu.MoApp.data.Dto.Group.Req.GroupPatchReq
import knu.MoApp.data.Dto.Group.Req.GroupPostReq
import knu.MoApp.data.Dto.Group.Res.GroupRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
interface GroupService {
    fun group(authentication: Authentication): ResponseEntity<ArrayList<GroupRes>>
    fun group(groupPostReq: GroupPostReq, authentication: Authentication): ResponseEntity<HttpStatus>
    fun group(groupPatchReq: GroupPatchReq, authentication: Authentication): ResponseEntity<HttpStatus>
    fun group(id: Int, authentication: Authentication): ResponseEntity<HttpStatus>
}