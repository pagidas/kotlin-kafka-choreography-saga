package org.example.model

import java.util.*

enum class OrderStatus { PENDING, APPROVED, REJECTED }
enum class OrderEventStatus { OrderCreated, OrderApproved, OrderRejected }

data class Order(val id: UUID = UUID.randomUUID(), val orderStatus: OrderStatus = OrderStatus.PENDING)
