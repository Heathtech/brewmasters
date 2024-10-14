package org.heathtech.tilt_webserver.frameworks.exposed.tilt

import org.heathtech.tilt_webserver.domain.data.tilt.TiltDataPoint
import org.heathtech.tilt_webserver.domain.data.tilt.TiltRepository
import org.heathtech.tilt_webserver.domain.extensions.toUuid
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
class ExposedTiltRepository : TiltRepository {
    override fun storeDataPoint(data: TiltDataPoint, createdAt: OffsetDateTime): Unit = transaction {
        TiltDao.new {
            device = TiltDeviceDao.find { TiltDeviceTable.uuid eq data.deviceUuid.toUuid() }.first()
            tiltTemp = data.tiltTemp
            tiltGravity = data.tiltGravity
            loggedAt = OffsetDateTime.now().toLocalDateTime()
        }
    }
}