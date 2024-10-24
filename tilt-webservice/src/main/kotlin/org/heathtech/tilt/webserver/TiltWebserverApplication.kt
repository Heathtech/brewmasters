package org.heathtech.tilt.webserver

import org.heathtech.tilt.webserver.frameworks.config.JacksonConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Import

@SpringBootApplication
class TiltWebserverApplication : SpringBootServletInitializer() {
    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder =
        application.sources(TiltWebserverApplication::class.java)
}

fun main(args: Array<String>) {
    runApplication<TiltWebserverApplication>(*args)
}
