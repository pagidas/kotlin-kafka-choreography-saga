package org.example.pantry.model

import java.util.*

data class PantryItem(val id: UUID = UUID.randomUUID(), val name: String, val quantityLimit: Int)