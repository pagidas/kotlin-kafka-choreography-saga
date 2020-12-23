package org.example.orders.repository

import org.example.orders.model.Order
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

object OrderRepository {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }

    private val dbConnection = OrderDatabase.connection

    fun insertOrder(order: Order) {
        val statement = dbConnection.createStatement()
        statement.executeUpdate("""
            insert into orders_app.orders values ('${order.id}', '${order.orderStatus.name}')
        """.trimIndent())
    }

    fun selectOrderById(id: UUID): Order = TODO()
}
