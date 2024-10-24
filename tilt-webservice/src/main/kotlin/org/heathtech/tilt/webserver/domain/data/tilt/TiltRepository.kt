package org.heathtech.tilt.webserver.domain.data.tilt

import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.UUID

@Repository
interface TiltRepository {
    fun storeDataPoint(data: TiltDataPoint)

    fun getDataPoints(
        tiltDeviceId: UUID? = null,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null,
        limit: Int? = null,
    ): List<TiltDataPoint>
}
