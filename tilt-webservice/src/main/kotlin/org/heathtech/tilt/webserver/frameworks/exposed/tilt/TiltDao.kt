package org.heathtech.tilt.webserver.frameworks.exposed.tilt

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object TiltDaoTable : IntIdTable("tilt_data") {
    val tiltId = reference("tilt_device", TiltDeviceTable)
    val tiltTemp = integer("tilt_temp")
    val gravity = integer("gravity")
    val battery = integer("battery").nullable()
    val rssi = integer("rssi").nullable()
    val loggedAt = datetime("logged_at")
}

class TiltDao(
    id: EntityID<Int>,
) : IntEntity(id) {
    companion object : IntEntityClass<TiltDao>(TiltDaoTable)

    var device by TiltDeviceDao referencedOn TiltDaoTable.tiltId
    var tiltTemp by TiltDaoTable.tiltTemp
    var gravity by TiltDaoTable.gravity
    var battery by TiltDaoTable.battery
    var rssi by TiltDaoTable.rssi
    var loggedAt by TiltDaoTable.loggedAt
}
