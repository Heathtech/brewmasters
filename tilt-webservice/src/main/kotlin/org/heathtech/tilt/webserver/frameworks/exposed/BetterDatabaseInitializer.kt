package org.heathtech.tilt.webserver.frameworks.exposed

import org.jetbrains.exposed.spring.DatabaseInitializer
import org.jetbrains.exposed.spring.discoverExposedTables
import org.jetbrains.exposed.sql.SchemaUtils
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class BetterDatabaseInitializer(
    val applicationContext: ApplicationContext,
    val excludedPackages: List<String>,
) : DatabaseInitializer(applicationContext, excludedPackages) {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun run(args: ApplicationArguments?) {
        val exposedTables = discoverExposedTables(applicationContext, excludedPackages)
        logger.info("Schema generation for tables '{}'", exposedTables.map { it.tableName })

        logger.info("ddl {}", exposedTables.map { it.ddl }.joinToString())
        SchemaUtils.createMissingTablesAndColumns(tables = exposedTables.toTypedArray())
    }
}
