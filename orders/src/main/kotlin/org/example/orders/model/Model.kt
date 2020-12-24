package org.example.orders.model

import java.util.*

enum class OrderStatus { PENDING, APPROVED, REJECTED }
enum class OrderEventType { OrderCreated, OrderApproved, OrderRejected }

data class Order(
    val id: UUID = UUID.randomUUID(),
    val orderStatus: OrderStatus = OrderStatus.PENDING,
    val pantryItemId: UUID,
    val quantity: Int)
