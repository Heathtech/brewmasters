package org.heathtech.tilt.webserver.frameworks.exposed.migration

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object MigrationTable : IntIdTable("migrations") {
    val version = integer("version")
    val appliedAt = datetime("applied_at")
}
