package org.example.orders.repository

import org.example.orders.exceptions.PantryItemNotFoundException
import org.example.orders.model.Order
import org.example.orders.model.OrderStatus
import org.postgresql.util.PSQLException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.Connection
import java.util.*

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
                insert into orders_app.orders values ('${order.id}', '${order.status.name}', '${order.pantryItemId}', '${order.pantryItemQuantity}')
                """.trimIndent())
            } catch (e: PSQLException) {
                throw PantryItemNotFoundException(e.message)
            }
        }
    }

    fun updateOrderStatus(orderId: UUID, orderStatus: OrderStatus): Order {
        log.info("Attempt to update order to $orderStatus")
        val result = dbConnection.createStatement().executeQuery(
            """
                with updated as (
                    update orders_app.orders
                        set status='${orderStatus.name}'
                        where id='$orderId'
                    returning *
                )
                select * from updated
        """.trimIndent())
        result.next()

        return Order(
            result.getObject("id", UUID::class.java),
            OrderStatus.valueOf(result.getString("status")),
            result.getObject("pantry_item_id", UUID::class.java),
            result.getInt("pantry_item_quantity")
        )
    }
}
