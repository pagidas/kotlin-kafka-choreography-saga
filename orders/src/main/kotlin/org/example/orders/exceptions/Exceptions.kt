package org.example.orders.exceptions

import java.lang.RuntimeException

// Repo level exceptions
data class PantryItemNotFoundException(override val message: String?) : RuntimeException(message)