package knu.MoApp.data.Dto.ShareSchedule.Req

import io.swagger.annotations.ApiModelProperty
import java.time.LocalDate

data class ShareSchedulePostUserScheduleReq(
    @ApiModelProperty(
        name = "공유방의 ID",
        value = "개인 일정을 추가하고 싶은 공유방의 ID를 입력합니다."
    )
    val id: Int,
    var startTime: Int,
    var endTime: Int,
    var date: LocalDate
)