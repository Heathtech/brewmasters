package org.heathtech.tilt_webserver.frameworks.exposed.tilt

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object TiltDeviceTable : IntIdTable("tilt_device") {
    val name = text("tilt_name").nullable()
    val uuid = uuid("tilt_uuid")
}

class TiltDeviceDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TiltDeviceDao>(TiltDeviceTable)

    var name by TiltDeviceTable.name
    var uuid by TiltDeviceTable.uuid
}