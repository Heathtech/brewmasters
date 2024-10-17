package org.heathtech.tilt.webserver.frameworks.exposed.tilt

import org.heathtech.tilt.webserver.domain.data.tilt.TiltDevice
import org.heathtech.tilt.webserver.domain.data.tilt.TiltDeviceRepository
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class ExposedTiltDeviceRepository : TiltDeviceRepository {
    override fun getDevices(): List<TiltDevice> =
        transaction {
            TiltDeviceDao.all().map { it.toModel() }
        }
}
