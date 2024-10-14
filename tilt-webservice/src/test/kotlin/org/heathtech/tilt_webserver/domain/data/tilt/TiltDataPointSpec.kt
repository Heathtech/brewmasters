package org.heathtech.tilt_webserver.domain.data.tilt

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.junit.jupiter.api.Test

class TiltDataPointSpec {
    @Test
    fun `Unknowns are preserved during deserialization`() {
        // Given
        val rawData = """{
            "deviceUuid":"88bb03fb-6549-4bf1-877d-45972fc6f657",
            "tiltTemp":69,
            "tiltGravity":1002,
            "ambientTemp":77
        }""".trimMargin()

        val parser = JsonMapper().registerKotlinModule()

        // When
        val dataPoint = parser.readValue<TiltDataPoint>(rawData)

        // Then
        assert(dataPoint.tiltTemp == 69)
        assert(dataPoint.unknowns.size == 1)
        assert(dataPoint.unknowns["ambientTemp"] == 77)
    }
}