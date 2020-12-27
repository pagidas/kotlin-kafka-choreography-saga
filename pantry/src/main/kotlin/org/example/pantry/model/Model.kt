package org.example.pantry.model

import java.util.*

enum class OrderEventType { OrderCreated, OrderUpdated }
enum class PantryEventType {
    PantryItemQuantityLimitCredited,
    PantryItemQuantityLimitFailed,
    PantryItemQuantityLimitRejected
}

data class PantryItem(val id: UUID = UUID.randomUUID(), val name: String, val quantityLimit: Int)