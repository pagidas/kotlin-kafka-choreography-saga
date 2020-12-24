package org.example.orders.repository

import org.example.orders.exceptions.PantryItemNotFoundException
import org.example.orders.model.Order
import org.postgresql.util.PSQLException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.util.*
import kotlin.jvm.Throws

object OrderRepository {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }

    private val dbConnection = OrderDatabase.connection

    private fun transaction(conn: Connection = dbConnection, fn: Connection.() -> Unit = {}) = conn.fn()

    @Throws(PantryItemNotFoundException::class)
    fun insertOrder(order: Order) {
        log.info("Attempt to insert order")
        transaction {
            try {
                createStatement().executeUpdate("""
                insert into orders_app.orders values ('${order.id}', '${order.orderStatus.name}', '${order.pantryItemId}')
                """.trimIndent())
            } catch (e: PSQLException) {
                throw PantryItemNotFoundException(e.message)
            }
        }
    }
}
