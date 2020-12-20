package org.example.model

import java.util.*

enum class Status { PENDING, APPROVED, REJECTED }

data class Order(val id: UUID, val status: Status = Status.PENDING)
