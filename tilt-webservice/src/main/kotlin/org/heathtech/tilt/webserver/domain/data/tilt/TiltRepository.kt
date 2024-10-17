package org.heathtech.tilt.webserver.domain.data.tilt

import org.springframework.stereotype.Repository

@Repository
interface TiltRepository {
    fun storeDataPoint(data: TiltDataPoint)
}
