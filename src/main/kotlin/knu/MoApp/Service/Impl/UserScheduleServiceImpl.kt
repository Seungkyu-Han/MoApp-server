package knu.MoApp.Service.Impl

import knu.MoApp.Repository.UserRepository
import knu.MoApp.Service.UserScheduleService
import org.springframework.stereotype.Service

@Service
class UserScheduleServiceImpl(val userRepository: UserRepository): UserScheduleService {
}