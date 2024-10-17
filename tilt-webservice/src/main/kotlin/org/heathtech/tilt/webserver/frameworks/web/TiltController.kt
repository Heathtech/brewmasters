package org.heathtech.tilt.webserver.frameworks.web

import org.heathtech.tilt.webserver.domain.data.tilt.TiltDataPoint
import org.heathtech.tilt.webserver.domain.data.tilt.TiltRepository
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
@RequestMapping("tilt")
class TiltController(
    val tiltRepository: TiltRepository,
) {
    @PostMapping("")
    fun uploadDataPoint(
        @RequestBody tiltDataPoint: TiltDataPoint,
        @RequestParam(value = "time", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS") loggedTime: LocalDateTime?,
    ) {
        tiltRepository.storeDataPoint(
            tiltDataPoint.copy(
                loggedTime = tiltDataPoint.loggedTime ?: loggedTime,
            ),
        )
    }
}
