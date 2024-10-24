package org.heathtech.tilt.webserver.frameworks.appready.migration

import org.heathtech.tilt.webserver.domain.extensions.toUuid
import org.heathtech.tilt.webserver.domain.migration.DatabaseMigration
import org.heathtech.tilt.webserver.frameworks.exposed.tilt.TiltDeviceDao
import org.heathtech.tilt.webserver.frameworks.exposed.tilt.TiltDeviceTable
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Component

@Component
class V002DevicesEnabled : DatabaseMigration {
    override val version: Int
        get() = 2

    override fun applyMigration(): Unit =
        transaction {
            val deviceList =
                listOf(
                    "a495bb30c5b14b44b5121370f02d74de".toUuid(), // Tilt Black
                    "a495bb00c5b14b44b5121370f02d74de".toUuid(), // Debug
                )
            TiltDeviceDao.find { TiltDeviceTable.uuid inList deviceList }.forEach {
                it.enabled = true
            }
        }
}
