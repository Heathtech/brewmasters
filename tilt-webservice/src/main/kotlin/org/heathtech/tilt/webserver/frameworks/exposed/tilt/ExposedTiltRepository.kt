package org.heathtech.tilt.webserver.frameworks.exposed.tilt

import org.heathtech.tilt.webserver.domain.data.tilt.TiltDataPoint
import org.heathtech.tilt.webserver.domain.data.tilt.TiltRepository
import org.heathtech.tilt.webserver.domain.extensions.toUuid
import org.heathtech.tilt.webserver.frameworks.exposed.tilt.TiltDaoTable.tiltId
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
class ExposedTiltRepository : TiltRepository {
    override fun storeDataPoint(data: TiltDataPoint): Unit =
        transaction {
            TiltDao.new {
                device = TiltDeviceDao.find { TiltDeviceTable.id eq data.deviceUuid.toUuid() }.first()
                tiltTemp = data.tiltTemp
                gravity = data.gravity
                battery = data.battery
                rssi = data.rssi
                loggedAt = data.loggedTime ?: LocalDateTime.now()
            }
        }

    override fun getDataPoints(
        tiltDeviceId: UUID?,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?,
        limit: Int?,
    ): List<TiltDataPoint> =
        transaction {
            val deviceTableId = tiltDeviceId?.let { TiltDeviceDao.find { TiltDeviceTable.id eq tiltDeviceId }.first() }
            val query = TiltDaoTable.selectAll()
            if (deviceTableId != null) query.andWhere { tiltId eq deviceTableId.id.value }
            if (startDate != null) query.andWhere { TiltDaoTable.loggedAt greaterEq startDate }
            if (endDate != null) query.andWhere { TiltDaoTable.loggedAt lessEq endDate }
            if (limit != null) query.limit(limit)
            query.orderBy(TiltDaoTable.loggedAt, SortOrder.DESC)
            TiltDao.wrapRows(query).map { it.toModel() }
        }
}
