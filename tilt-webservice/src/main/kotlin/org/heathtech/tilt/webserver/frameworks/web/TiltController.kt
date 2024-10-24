package org.heathtech.tilt.webserver.frameworks.web

import org.heathtech.tilt.webserver.domain.data.tilt.TiltDataPoint
import org.heathtech.tilt.webserver.domain.data.tilt.TiltRepository
import org.heathtech.tilt.webserver.domain.extensions.toUuid
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.util.UUID

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

    @GetMapping("")
    fun getAllTiltData(
        @RequestParam("device", required = false) deviceId: String?,
        @RequestParam("start", required = false) startDate: LocalDateTime?,
        @RequestParam("end", required = false) endDate: LocalDateTime?,
        @RequestParam("limit", required = false) limit: Int?,
    ) = tiltRepository.getDataPoints(deviceId?.toUuid(), startDate, endDate, limit)

    @GetMapping("/latest")
    fun getLatestTiltData(
        @RequestParam("limit", required = false) limit: Int?,
    ) = tiltRepository.getDataPoints(limit = limit ?: 1)

    @GetMapping("/{tiltDeviceId}/data")
    fun getTiltData(
        @PathVariable tiltDeviceId: UUID,
        @RequestParam("start", required = false) startDate: LocalDateTime?,
        @RequestParam("end", required = false) endDate: LocalDateTime?,
        @RequestParam("limit", required = false) limit: Int?,
    ) = tiltRepository.getDataPoints(tiltDeviceId, startDate, endDate)
}
