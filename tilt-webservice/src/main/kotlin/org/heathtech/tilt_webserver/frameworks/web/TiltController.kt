package org.heathtech.tilt_webserver.frameworks.web

import org.heathtech.tilt_webserver.domain.data.tilt.TiltDataPoint
import org.heathtech.tilt_webserver.domain.data.tilt.TiltRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime

@RestController
@RequestMapping("tilt")
class TiltController(
    val tiltRepository: TiltRepository,
) {
    @PostMapping("")
    fun uploadDataPoint(@RequestBody tiltDataPoint: TiltDataPoint) {
        tiltRepository.storeDataPoint(tiltDataPoint, OffsetDateTime.now())
    }
}