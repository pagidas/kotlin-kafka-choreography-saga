package org.example.orders.repository

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.example.orders.pubsub.config
import org.flywaydb.core.Flyway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.Connection

private val postgresConfig = config.getObject("postgres").toConfig()

object OrderDatabase {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }

    private val datasource =
        HikariDataSource(
            HikariConfig().apply {
                this.driverClassName = postgresConfig.getString("driver")
                jdbcUrl = postgresConfig.getString("url")
                username = postgresConfig.getString("username")
                password = postgresConfig.getString("password")
                validate()
            }
    )

    val connection: Connection = datasource.connection

    init {
        log.info("Attempt to run migrations")
        val flyway = Flyway.configure().dataSource(datasource).load()
        flyway.migrate()
    }
}