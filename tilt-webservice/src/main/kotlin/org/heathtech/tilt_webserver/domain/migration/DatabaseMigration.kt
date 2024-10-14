package org.heathtech.tilt_webserver.domain.migration

interface DatabaseMigration {
    /** Runs [applyMigration] only if the latest DB migration is less than this value */
    val version: Int

    fun applyMigration()
}