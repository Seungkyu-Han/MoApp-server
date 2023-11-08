package knu.MoApp.Service.Impl

import knu.MoApp.Repository.UserRepository
import knu.MoApp.Service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    val userRepository: UserRepository
) :UserService{


}