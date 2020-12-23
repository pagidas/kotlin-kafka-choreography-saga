package org.example.orders.repository

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.example.orders.model.Order
import org.example.orders.pubsub.config
import org.flywaydb.core.Flyway
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

private val postgresConfig = config.getObject("postgres").toConfig()

object OrderRepository {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }

    private val ordersDataSource =
        HikariDataSource(
            HikariConfig().apply {
                this.driverClassName = postgresConfig.getString("driver")
                jdbcUrl = postgresConfig.getString("url")
                username = postgresConfig.getString("username")
                password = postgresConfig.getString("password")
                validate()
            }
        )

    init {
        log.info("Attempt to run migrations")
        val flyway = Flyway.configure().dataSource(ordersDataSource).load()
        flyway.migrate()
    }

    fun insertOrder(order: Order): Order = TODO()

    fun selectOrderById(id: UUID): Order = TODO()
}
