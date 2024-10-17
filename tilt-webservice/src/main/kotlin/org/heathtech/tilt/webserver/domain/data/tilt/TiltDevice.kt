package org.heathtech.tilt.webserver.domain.data.tilt

import java.util.UUID

data class TiltDevice(
    val uuid: UUID,
    val name: String,
    val debug: Boolean,
)
