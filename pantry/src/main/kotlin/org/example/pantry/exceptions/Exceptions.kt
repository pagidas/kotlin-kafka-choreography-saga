package org.example.pantry.exceptions

import java.lang.RuntimeException

// Repo level exceptions
data class PantryItemNotFoundException(override val message: String?) : RuntimeException(message)

// Business level exceptions
data class PantryItemQuantityLimitExceeded(override val message: String?): RuntimeException(message)