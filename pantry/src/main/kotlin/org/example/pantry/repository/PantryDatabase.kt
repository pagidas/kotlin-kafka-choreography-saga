package org.example.pantry.repository

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.example.pantry.pubsub.config
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.Connection

private val postgresConfig = config.getObject("postgres").toConfig()

object PantryDatabase {
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
}
