package org.heathtech.tilt.webserver.domain.data.tilt

interface TiltDeviceRepository {
    fun getDevices(): List<TiltDevice>
}
