package org.heathtech.tilt.webserver.domain.data.beer

import org.heathtech.tilt.webserver.frameworks.exposed.tilt.TiltDeviceTable.uuid
import java.time.LocalDateTime
import java.util.UUID

data class CreateBeerRequest(
    val name: String,
    val tiltDeviceId: UUID,
    val startDate: LocalDateTime = LocalDateTime.now(),
) {
    fun toBeer(uuid: UUID) =
        Beer(
            uuid = uuid,
            name = name,
            tiltDeviceId = tiltDeviceId,
            startDate = startDate,
        )
}
