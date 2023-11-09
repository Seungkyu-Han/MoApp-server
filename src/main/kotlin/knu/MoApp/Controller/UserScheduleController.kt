package knu.MoApp.Controller

import io.swagger.annotations.Api
import knu.MoApp.Service.UserScheduleService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user-schedule")
@Api(description = "사용자 스케줄 관련 API")
class UserScheduleController(private val userScheduleService: UserScheduleService) {
}