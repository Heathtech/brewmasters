package org.heathtech.tilt.webserver.frameworks.exposed.tilt

import org.heathtech.tilt.webserver.domain.data.tilt.TiltDataPoint
import org.heathtech.tilt.webserver.domain.data.tilt.TiltRepository
import org.heathtech.tilt.webserver.domain.extensions.toUuid
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class ExposedTiltRepository : TiltRepository {
    override fun storeDataPoint(data: TiltDataPoint): Unit =
        transaction {
            TiltDao.new {
                device = TiltDeviceDao.find { TiltDeviceTable.uuid eq data.deviceUuid.toUuid() }.first()
                tiltTemp = data.tiltTemp
                gravity = data.gravity
                battery = data.battery
                rssi = data.rssi
                loggedAt = data.loggedTime ?: LocalDateTime.now()
            }
        }
}
