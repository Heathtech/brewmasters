package org.heathtech.tilt_webserver.frameworks.exposed.tilt

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.OffsetDateTime

object TiltDaoTable : IntIdTable("tilt_data") {
    val tiltId = reference("tilt_device", TiltDeviceTable)
    val tiltTemp = integer("tilt_temp")
    val tiltGravity = integer("tilt_gravity")
    val loggedAt = datetime("logged_at")
}

class TiltDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TiltDao>(TiltDaoTable)

    var device by TiltDeviceDao referencedOn TiltDaoTable.tiltId
    var tiltTemp by TiltDaoTable.tiltTemp
    var tiltGravity by TiltDaoTable.tiltGravity
    var loggedAt by TiltDaoTable.loggedAt
}