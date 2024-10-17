package org.heathtech.tilt.webserver.domain.data.beer

import java.time.LocalDateTime
import java.util.UUID

data class UpdateBeerRequest(
    val name: String?,
    val tiltDeviceId: UUID?,
    val startDate: LocalDateTime?,
    val endDate: LocalDateTime?,
)
