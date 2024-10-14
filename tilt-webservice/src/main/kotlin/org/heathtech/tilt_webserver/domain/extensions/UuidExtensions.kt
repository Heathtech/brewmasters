package org.heathtech.tilt_webserver.domain.extensions

import java.util.*

fun String.toUuid() = UUID.fromString(
    when {
        length == 32 -> replace("(.{8})(.{4})(.{4})(.{4})(.+)".toRegex(), "$1-$2-$3-$4-$5")
        else -> this
    }
)