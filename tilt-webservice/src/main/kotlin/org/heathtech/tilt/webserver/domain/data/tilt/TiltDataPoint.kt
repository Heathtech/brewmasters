package org.heathtech.tilt.webserver.domain.data.tilt

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonAnySetter
import java.time.LocalDateTime

data class TiltDataPoint(
    @JsonAlias("uuid")
    val deviceUuid: String,
    @JsonAlias("temp", "major")
    val tiltTemp: Int,
    @JsonAlias("minor")
    val gravity: Int,
    @JsonAlias("tx_power")
    val battery: Int? = null,
    val rssi: Int? = null,
    val mac: String? = null, // We'll accept this field, but it won't be saved. Hope I don't regret that decision
    val loggedTime: LocalDateTime? = null, // Overrides log time in place of now()
) {
    @JsonAnySetter
    val unknowns: Map<String, Any> = hashMapOf()
}
