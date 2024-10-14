package org.heathtech.tilt_webserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TiltWebserverApplication

fun main(args: Array<String>) {
	runApplication<TiltWebserverApplication>(*args)
}
