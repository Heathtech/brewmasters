package org.heathtech.tilt_webserver.domain.data.tilt

import com.fasterxml.jackson.annotation.JsonAlias
import com.fasterxml.jackson.annotation.JsonAnySetter
import java.util.UUID

data class TiltDataPoint(
    @JsonAlias("uuid")
    val deviceUuid: String,
    @JsonAlias("temp")
    val tiltTemp: Int,
    @JsonAlias("gravity")
    val tiltGravity: Int,
) {
    @JsonAnySetter
    val unknowns: Map<String, Any> = hashMapOf()
}