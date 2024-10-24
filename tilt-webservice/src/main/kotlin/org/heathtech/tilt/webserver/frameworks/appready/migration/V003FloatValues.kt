package org.heathtech.tilt.webserver.frameworks.appready.migration

import org.heathtech.tilt.webserver.domain.migration.DatabaseMigration
import org.heathtech.tilt.webserver.frameworks.exposed.tilt.TiltDaoTable
import org.jetbrains.exposed.sql.replace
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.vendors.SQLiteDialect
import org.jetbrains.exposed.sql.vendors.currentDialect
import org.springframework.stereotype.Component

@Component
class V003FloatValues : DatabaseMigration {
    override val version: Int
        get() = 2

    override fun applyMigration(): Unit =
        transaction {
            when (currentDialect) {
                is SQLiteDialect -> {
                    // SQLite is a pain: https://sqlite.org/lang_altertable.html

                    // Use CREATE TABLE to construct a new table "new_X" that is in the desired revised format of table X. Make sure that the name "new_X" does not collide with any existing table name, of course.
                    TiltDaoTable.createStatement().first { it.contains("CREATE TABLE") }.let {
                        exec(it.replace("tilt_data", "tilt_data_temp"))
                    }

                    // Transfer content from X into new_X using a statement like: INSERT INTO new_X SELECT ... FROM X.
                    val columns = "id,tilt_device,tilt_temp,gravity,battery,rssi,logged_at"
                    exec("INSERT INTO tilt_data_temp($columns) SELECT $columns FROM tilt_data")

                    // Drop the old table X: DROP TABLE X.
                    exec("DROP TABLE tilt_data")

                    // Change the name of new_X to X using: ALTER TABLE new_X RENAME TO X.
                    exec("ALTER TABLE tilt_data_temp RENAME TO tilt_data")
                }
                else -> {
                    exec("ALTER TABLE tilt_data MODIFY tilt_temp ${db.dialect.dataTypeProvider.floatType()}")
                    exec("ALTER TABLE tilt_data MODIFY gravity ${db.dialect.dataTypeProvider.floatType()}")
                }
            }
        }
}
