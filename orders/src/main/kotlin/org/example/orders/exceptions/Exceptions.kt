package org.example.orders.exceptions

// Repo level exceptions
data class PantryItemNotFoundException(override val message: String?) : RuntimeException(message)