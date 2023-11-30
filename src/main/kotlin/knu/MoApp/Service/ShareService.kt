package knu.MoApp.Service

import knu.MoApp.data.Dto.Share.Req.SharePatchReq
import knu.MoApp.data.Dto.Share.Req.SharePostReq
import knu.MoApp.data.Dto.Share.Res.ShareNearRes
import knu.MoApp.data.Dto.Share.Res.ShareRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
interface ShareService {
    fun share(authentication: Authentication): ResponseEntity<ArrayList<ShareRes>>
    fun share(sharePostReq: SharePostReq, authentication: Authentication): ResponseEntity<HttpStatus>
    fun share(sharePatchReq: SharePatchReq, authentication: Authentication): ResponseEntity<HttpStatus>
    fun share(id: Int, authentication: Authentication): ResponseEntity<HttpStatus>
    fun near(authentication: Authentication): ResponseEntity<ShareNearRes>
    fun info(id: Int, authentication: Authentication): ResponseEntity<ShareRes>
}