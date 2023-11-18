package knu.MoApp.Service.Impl

import knu.MoApp.Repository.ShareRepository
import knu.MoApp.Repository.ShareUserRepository
import knu.MoApp.Repository.UserRepository
import knu.MoApp.Service.ShareService
import knu.MoApp.data.Dto.Share.Req.SharePatchReq
import knu.MoApp.data.Dto.Share.Req.SharePostReq
import knu.MoApp.data.Dto.Share.Res.ShareRes
import knu.MoApp.data.Dto.User.Res.UserInfoRes
import knu.MoApp.data.Entity.Embedded.ShareUserRelation
import knu.MoApp.data.Entity.Share
import knu.MoApp.data.Entity.ShareUser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class ShareServiceImpl(
    private val userRepository: UserRepository,
    private val shareRepository: ShareRepository,
    private val shareUserRepository: ShareUserRepository
): ShareService {

    override fun share(authentication: Authentication): ResponseEntity<ArrayList<ShareRes>> {

        val user = userRepository.findById(Integer.valueOf(authentication.name))

        val response = ArrayList<ShareRes>()

        if(user.isEmpty)
            return ResponseEntity(null, HttpStatus.FORBIDDEN)

        val shareList = shareUserRepository.findShareByUser(user.get())

        for(share in shareList){
            val userList = shareUserRepository.findUserByGroup(share)
            val userInfoList = ArrayList<UserInfoRes>()
            for(userInGroup in userList)
                userInfoList.add(UserInfoRes(userInGroup))
            response.add(ShareRes(id = share.id ?: 0,
                name = share.name,
                userInfoResList = userInfoList,
                startDate = share.startDate,
                endDate = share.endDate))
        }

        return ResponseEntity(response, HttpStatus.OK)
    }

    override fun share(sharePostReq: SharePostReq, authentication: Authentication): ResponseEntity<HttpStatus> {

        val user = userRepository.findById(Integer.valueOf(authentication.name))

        if(user.isEmpty)
            return ResponseEntity(null, HttpStatus.FORBIDDEN)

        if(sharePostReq.endDate < sharePostReq.startDate)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        val share = Share(
            id = null,
            name = sharePostReq.name,
            startDate = sharePostReq.startDate,
            endDate = sharePostReq.endDate
        )

        shareRepository.save(share)

        for(userId in sharePostReq.userIdList){
            val addUser = userRepository.findById(userId)
            if(addUser.isEmpty)
                return ResponseEntity(HttpStatus.BAD_REQUEST)
            shareUserRepository.save(ShareUser(ShareUserRelation(addUser.get(), share)))
        }
        shareUserRepository.save(ShareUser(ShareUserRelation(user.get(), share)))

        return ResponseEntity(HttpStatus.OK)
    }

    override fun share(sharePatchReq: SharePatchReq, authentication: Authentication): ResponseEntity<HttpStatus> {
        val user = userRepository.findById(Integer.valueOf(authentication.name))

        if(user.isEmpty)
            return ResponseEntity(null, HttpStatus.FORBIDDEN)

        val share = shareRepository.findById(sharePatchReq.id)

        if(share.isEmpty)
            return ResponseEntity(null, HttpStatus.BAD_REQUEST)

        if(sharePatchReq.name != null)
            share.get().name = sharePatchReq.name

        if(sharePatchReq.startDate != null)
            share.get().startDate = sharePatchReq.startDate

        if(sharePatchReq.endDate != null)
            share.get().endDate = sharePatchReq.endDate

        shareRepository.save(share.get())

        return ResponseEntity(HttpStatus.OK)
    }

    override fun share(id: Int, authentication: Authentication): ResponseEntity<HttpStatus> {
        val user = userRepository.findById(Integer.valueOf(authentication.name))

        if(user.isEmpty)
            return ResponseEntity(null, HttpStatus.FORBIDDEN)

        val share = shareRepository.findById(id)

        if(share.isEmpty)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        val shareUser = shareUserRepository.findById(ShareUserRelation(user = user.get(), share = share.get()))

        if(shareUser.isEmpty)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        shareUserRepository.delete(shareUser.get())

        //만약에 0명이면 아예 삭제하기

        if(!shareUserRepository.existsByShareUserRelationShare(share.get()))
            shareRepository.delete(share.get())

        return ResponseEntity(HttpStatus.OK)
    }
}