package org.heathtech.tilt.webserver.frameworks.web

import org.heathtech.tilt.webserver.domain.data.tilt.TiltDeviceRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("devices")
class DevicesController(
    val tiltDeviceRepository: TiltDeviceRepository,
) {
    @GetMapping("")
    fun getDevices() = tiltDeviceRepository.getDevices()
}
