package org.example.orders.model

import java.util.*

enum class OrderStatus { PENDING, APPROVED, FAILED, REJECTED }
enum class OrderEventType { OrderCreated, OrderApproved, OrderRejected }
enum class PantryEventType {
    PantryItemQuantityLimitCredited,
    PantryItemQuantityLimitFailed,
    PantryItemQuantityLimitRejected
}

data class Order(
    val id: UUID = UUID.randomUUID(),
    val orderStatus: OrderStatus = OrderStatus.PENDING,
    val pantryItemId: UUID,
    val quantity: Int)
