package org.example.initdb

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun main() {
    val log: Logger by lazy { LoggerFactory.getLogger("org.example.initdb.DbMigrations") }

    val postgresConfig = ConfigFactory.load().getObject("postgres").toConfig()

    val datasource =
        HikariDataSource(
            HikariConfig().apply {
                this.driverClassName = postgresConfig.getString("driver")
                jdbcUrl = postgresConfig.getString("url")
                username = postgresConfig.getString("username")
                password = postgresConfig.getString("password")
                validate()
            }
        )

    log.info("Attempt to run migrations")
    Flyway
        .configure()
        .dataSource(datasource)
        .load()
        .migrate()
}