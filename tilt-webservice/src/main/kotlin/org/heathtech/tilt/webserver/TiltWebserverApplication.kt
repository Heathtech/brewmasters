package org.heathtech.tilt.webserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

@SpringBootApplication
class TiltWebserverApplication : SpringBootServletInitializer() {
    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder =
        application.sources(TiltWebserverApplication::class.java)
}

fun main(args: Array<String>) {
    runApplication<TiltWebserverApplication>(*args)
}
