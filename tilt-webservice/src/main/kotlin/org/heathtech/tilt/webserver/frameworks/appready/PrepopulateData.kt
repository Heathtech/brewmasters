package org.heathtech.tilt.webserver.frameworks.appready

import org.heathtech.tilt.webserver.domain.migration.DatabaseMigration
import org.heathtech.tilt.webserver.frameworks.exposed.migration.MigrationTable
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import java.time.OffsetDateTime

@Component
class PrepopulateData(
    val migrations: List<DatabaseMigration>,
) {
    @EventListener(ApplicationReadyEvent::class)
    fun runDatabaseMigrations() =
        transaction {
            SchemaUtils.createMissingTablesAndColumns()

            val latestVersion =
                MigrationTable
                    .select(MigrationTable.version)
                    .orderBy(MigrationTable.version, order = SortOrder.DESC)
                    .map {
                        it[MigrationTable.version]
                    }.firstOrNull() ?: -1

            val maxAppliedVersion =
                migrations
                    .filter { it.version > latestVersion }
                    .takeUnless { it.isEmpty() }
                    ?.maxOf {
                        it.applyMigration()
                        it.version
                    }
                    ?: return@transaction // No versions to apply, so exit here

            MigrationTable.insert {
                it[version] = maxAppliedVersion
                it[appliedAt] = OffsetDateTime.now().toLocalDateTime()
            }
        }
}
