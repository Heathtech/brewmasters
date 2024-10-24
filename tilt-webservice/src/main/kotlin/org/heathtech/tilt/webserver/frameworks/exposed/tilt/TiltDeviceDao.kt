package org.heathtech.tilt.webserver.frameworks.exposed.tilt

import org.heathtech.tilt.webserver.domain.data.tilt.TiltDevice
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

object TiltDeviceTable : UUIDTable("tilt_device") {
    val name = text("tilt_name").nullable()
    val debug = bool("debug").default(false)
    val enabled = bool("enabled").default(false)
}

class TiltDeviceDao(
    id: EntityID<UUID>,
) : UUIDEntity(id) {
    companion object : UUIDEntityClass<TiltDeviceDao>(TiltDeviceTable)

    var name by TiltDeviceTable.name
    var debug by TiltDeviceTable.debug
    var enabled by TiltDeviceTable.enabled

    fun toModel() =
        TiltDevice(
            uuid = id.value,
            name = name ?: "untitled beer",
            debug = debug,
            enabled = enabled,
        )
}
