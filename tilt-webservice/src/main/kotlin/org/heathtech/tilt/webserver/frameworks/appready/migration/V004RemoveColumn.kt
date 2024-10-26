package org.heathtech.tilt.webserver.frameworks.appready.migration

import org.heathtech.tilt.webserver.domain.migration.DatabaseMigration
import org.heathtech.tilt.webserver.frameworks.exposed.tilt.TiltDaoTable
import org.heathtech.tilt.webserver.frameworks.exposed.tilt.TiltDeviceTable
import org.jetbrains.exposed.sql.replace
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.vendors.SQLiteDialect
import org.jetbrains.exposed.sql.vendors.currentDialect
import org.springframework.stereotype.Component

@Component
class V004RemoveColumn : DatabaseMigration {
    override val version: Int
        get() = 4

    override fun applyMigration(): Unit =
        transaction {
            when (currentDialect) {
                is SQLiteDialect -> {
                    // SQLite is a pain: https://sqlite.org/lang_altertable.html

                    // Use CREATE TABLE to construct a new table "new_X" that is in the desired revised format of table X. Make sure that the name "new_X" does not collide with any existing table name, of course.
                    TiltDeviceTable.createStatement().first { it.contains("CREATE TABLE") }.let {
                        exec(it.replace("tilt_device", "tilt_device_temp"))
                    }

                    // Transfer content from X into new_X using a statement like: INSERT INTO new_X SELECT ... FROM X.
                    val columns = "id, tilt_name, debug, enabled"
                    exec("INSERT INTO tilt_device_temp($columns) SELECT $columns FROM tilt_device")

                    // Drop the old table X: DROP TABLE X.
                    exec("DROP TABLE tilt_device")

                    // Change the name of new_X to X using: ALTER TABLE new_X RENAME TO X.
                    exec("ALTER TABLE tilt_device_temp RENAME TO tilt_device")
                }
                else -> {
                    exec("ALTER TABLE tilt_device DROP COLUMN tilt_uuid")
                }
            }
        }
}
