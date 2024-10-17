package org.heathtech.tilt.webserver.frameworks.exposed.beer

import org.heathtech.tilt.webserver.domain.data.beer.Beer
import org.heathtech.tilt.webserver.frameworks.exposed.tilt.TiltDeviceDao
import org.heathtech.tilt.webserver.frameworks.exposed.tilt.TiltDeviceTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime
import java.util.UUID

object BeerTable : UUIDTable("beers") {
    val name = text("name").nullable()
    val tiltDevice = reference("device", TiltDeviceTable)
    val startedAt = datetime("start")
}

class BeerDao(
    id: EntityID<UUID>,
) : UUIDEntity(id) {
    companion object : UUIDEntityClass<BeerDao>(BeerTable)

    var name by BeerTable.name
    var tiltDevice by TiltDeviceDao referencedOn BeerTable.tiltDevice
    var startedAt by BeerTable.startedAt

    fun toModel(endedAt: LocalDateTime?) =
        Beer(
            uuid = id.value,
            name = name ?: "untitled beer",
            tiltDeviceId = tiltDevice.uuid,
            startDate = startedAt,
            endDate = endedAt,
        )
}
