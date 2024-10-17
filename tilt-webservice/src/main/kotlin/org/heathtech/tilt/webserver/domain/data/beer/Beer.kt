package org.heathtech.tilt.webserver.domain.data.beer

import java.time.LocalDateTime
import java.util.UUID

data class Beer(
    val uuid: UUID,
    val name: String,
    val tiltDeviceId: UUID,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime? = null,
)
