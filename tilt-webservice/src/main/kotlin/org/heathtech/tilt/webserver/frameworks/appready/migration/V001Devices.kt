package org.heathtech.tilt.webserver.frameworks.appready.migration

import org.heathtech.tilt.webserver.domain.extensions.toUuid
import org.heathtech.tilt.webserver.domain.migration.DatabaseMigration
import org.heathtech.tilt.webserver.frameworks.exposed.tilt.TiltDeviceDao
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Component

@Component
class V001Devices : DatabaseMigration {
    override val version: Int
        get() = 1

    override fun applyMigration(): Unit =
        transaction {
            TiltDeviceDao.new {
                name = "Tilt Red"
                uuid = "a495bb10c5b14b44b5121370f02d74de".toUuid()
            }
            TiltDeviceDao.new {
                name = "Tilt Green"
                uuid = "a495bb20c5b14b44b5121370f02d74de".toUuid()
            }
            TiltDeviceDao.new {
                name = "Tilt Black"
                uuid = "a495bb30c5b14b44b5121370f02d74de".toUuid()
            }
            TiltDeviceDao.new {
                name = "Tilt Purple"
                uuid = "a495bb40c5b14b44b5121370f02d74de".toUuid()
            }
            TiltDeviceDao.new {
                name = "Tilt Orange"
                uuid = "a495bb50c5b14b44b5121370f02d74de".toUuid()
            }
            TiltDeviceDao.new {
                name = "Tilt Blue"
                uuid = "a495bb60c5b14b44b5121370f02d74de".toUuid()
            }
            TiltDeviceDao.new {
                name = "Tilt Yellow"
                uuid = "a495bb70c5b14b44b5121370f02d74de".toUuid()
            }
            TiltDeviceDao.new {
                name = "Tilt Pink"
                uuid = "a495bb80c5b14b44b5121370f02d74de".toUuid()
            }
            TiltDeviceDao.new {
                name = "Debugging"
                uuid = "a495bb00c5b14b44b5121370f02d74de".toUuid()
                debug = true
            }
        }
}
