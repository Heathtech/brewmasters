package org.heathtech.tilt.webserver.frameworks.config

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class JacksonConfig {
    //@Bean
    fun dateTimeModule() = JavaTimeModule()
}