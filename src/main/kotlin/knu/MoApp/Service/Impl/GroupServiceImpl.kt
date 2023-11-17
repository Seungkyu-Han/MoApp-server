package knu.MoApp.Service.Impl

import knu.MoApp.Repository.ShareRepository
import knu.MoApp.Repository.ShareUserRepository
import knu.MoApp.Repository.UserRepository
import knu.MoApp.Service.GroupService
import knu.MoApp.data.Dto.Group.Res.GroupRes
import knu.MoApp.data.Dto.User.Res.UserInfoRes
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
            response.add(GroupRes(id = group.id,
                name = group.name,
                userInfoResList = userInfoList,
                startDate = group.startDate,
                endDate = group.endDate))
        }

        return ResponseEntity(response, HttpStatus.OK)
    }

}