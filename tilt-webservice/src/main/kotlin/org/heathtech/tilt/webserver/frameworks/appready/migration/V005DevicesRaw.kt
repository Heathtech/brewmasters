package org.heathtech.tilt.webserver.frameworks.appready.migration

import org.heathtech.tilt.webserver.domain.extensions.toUuid
import org.heathtech.tilt.webserver.domain.migration.DatabaseMigration
import org.heathtech.tilt.webserver.frameworks.exposed.tilt.TiltDeviceDao
import org.heathtech.tilt.webserver.frameworks.exposed.tilt.TiltDeviceTable
import org.jetbrains.exposed.sql.deleteAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Component

@Component
class V004DevicesRaw : DatabaseMigration {
    override val version: Int
        get() = 4

    override fun applyMigration(): Unit =
        transaction {
            TiltDeviceTable.deleteAll()
            TiltDeviceDao.new(id = "a495bb10c5b14b44b5121370f02d74de".toUuid()) {
                name = "Tilt Red"
                enabled = false
            }
            TiltDeviceDao.new(id = "a495bb20c5b14b44b5121370f02d74de".toUuid()) {
                name = "Tilt Green"
                enabled = false
            }
            TiltDeviceDao.new(id = "a495bb30c5b14b44b5121370f02d74de".toUuid()) {
                name = "Tilt Black"
                enabled = true
            }
            TiltDeviceDao.new(id = "a495bb40c5b14b44b5121370f02d74de".toUuid()) {
                name = "Tilt Purple"
                enabled = false
            }
            TiltDeviceDao.new(id = "a495bb50c5b14b44b5121370f02d74de".toUuid()) {
                name = "Tilt Orange"
                enabled = false
            }
            TiltDeviceDao.new(id = "a495bb60c5b14b44b5121370f02d74de".toUuid()) {
                name = "Tilt Blue"
                enabled = false
            }
            TiltDeviceDao.new(id = "a495bb70c5b14b44b5121370f02d74de".toUuid()) {
                name = "Tilt Yellow"
                enabled = false
            }
            TiltDeviceDao.new(id = "a495bb80c5b14b44b5121370f02d74de".toUuid()) {
                name = "Tilt Pink"
                enabled = false
            }
            TiltDeviceDao.new(id = "a495bb00c5b14b44b5121370f02d74de".toUuid()) {
                name = "Debugging"
                enabled = true
                debug = true
            }
        }
}
