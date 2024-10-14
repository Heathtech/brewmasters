package org.heathtech.tilt_webserver.domain.data.tilt

import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
interface TiltRepository {
    fun storeDataPoint(data: TiltDataPoint, createdAt: OffsetDateTime)
}