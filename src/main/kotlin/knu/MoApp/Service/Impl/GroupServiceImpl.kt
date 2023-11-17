package knu.MoApp.Service.Impl

import knu.MoApp.Repository.ShareRepository
import knu.MoApp.Repository.ShareUserRepository
import knu.MoApp.Repository.UserRepository
import knu.MoApp.Service.GroupService
import knu.MoApp.data.Dto.Group.Req.GroupPatchReq
import knu.MoApp.data.Dto.Group.Req.GroupPostReq
import knu.MoApp.data.Dto.Group.Res.GroupRes
import knu.MoApp.data.Dto.User.Res.UserInfoRes
import knu.MoApp.data.Entity.Embedded.ShareUserRelation
import knu.MoApp.data.Entity.Share
import knu.MoApp.data.Entity.ShareUser
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class GroupServiceImpl(
    private val userRepository: UserRepository,
    private val shareRepository: ShareRepository,
    private val shareUserRepository: ShareUserRepository
): GroupService {

    override fun group(authentication: Authentication): ResponseEntity<ArrayList<GroupRes>> {

        val user = userRepository.findById(Integer.valueOf(authentication.name))

        val response = ArrayList<GroupRes>()

        if(user.isEmpty)
            return ResponseEntity(null, HttpStatus.FORBIDDEN)

        val shareList = shareUserRepository.findShareByUser(user.get())

        for(group in shareList){
            val userList = shareUserRepository.findUserByGroup(group)
            val userInfoList = ArrayList<UserInfoRes>()
            for(userInGroup in userList)
                userInfoList.add(UserInfoRes(userInGroup))
            response.add(GroupRes(id = group.id ?: 0,
                name = group.name,
                userInfoResList = userInfoList,
                startDate = group.startDate,
                endDate = group.endDate))
        }

        return ResponseEntity(response, HttpStatus.OK)
    }

    override fun group(groupPostReq: GroupPostReq, authentication: Authentication): ResponseEntity<HttpStatus> {

        val user = userRepository.findById(Integer.valueOf(authentication.name))

        if(user.isEmpty)
            return ResponseEntity(null, HttpStatus.FORBIDDEN)

        if(groupPostReq.endDate < groupPostReq.startDate)
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        val share = Share(
            id = null,
            name = groupPostReq.name,
            startDate = groupPostReq.startDate,
            endDate = groupPostReq.endDate
        )

        shareRepository.save(share)

        for(userId in groupPostReq.userIdList){
            val addUser = userRepository.findById(userId)
            if(addUser.isEmpty)
                return ResponseEntity(HttpStatus.BAD_REQUEST)
            shareUserRepository.save(ShareUser(ShareUserRelation(addUser.get(), share)))
        }
        shareUserRepository.save(ShareUser(ShareUserRelation(user.get(), share)))

        return ResponseEntity(HttpStatus.OK)
    }

    override fun group(groupPatchReq: GroupPatchReq, authentication: Authentication): ResponseEntity<HttpStatus> {
        val user = userRepository.findById(Integer.valueOf(authentication.name))

        if(user.isEmpty)
            return ResponseEntity(null, HttpStatus.FORBIDDEN)

        val share = shareRepository.findById(groupPatchReq.id)

        if(share.isEmpty)
            return ResponseEntity(null, HttpStatus.BAD_REQUEST)

        if(groupPatchReq.name != null)
            share.get().name = groupPatchReq.name

        if(groupPatchReq.startDate != null)
            share.get().startDate = groupPatchReq.startDate

        if(groupPatchReq.endDate != null)
            share.get().endDate = groupPatchReq.endDate

        shareRepository.save(share.get())

        return ResponseEntity(HttpStatus.OK)
    }

    override fun group(id: Int, authentication: Authentication): ResponseEntity<HttpStatus> {
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