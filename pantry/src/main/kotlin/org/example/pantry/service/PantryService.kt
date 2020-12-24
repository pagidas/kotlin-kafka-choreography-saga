package org.example.pantry.service

import org.example.avro.order.events.OrderEvent
import org.example.pantry.exceptions.PantryItemQuantityLimitExceeded
import org.example.pantry.exceptions.PantryItemNotFoundException
import org.example.pantry.model.PantryItem
import org.example.pantry.repository.PantryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

object PantryService {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }

    private val pantryRepo = PantryRepository
    private val pantryProducer = PantryProducer

    fun handleOrderCreatedEvent(createdOrderEvent: OrderEvent) {
        log.info("Attempt to credit item quantity limit")
        try {
            val item = pantryRepo.selectPantryItemById(UUID.fromString(createdOrderEvent.pantryItemId))
            try {
                item.creditQuantityLimit(createdOrderEvent.quantity)
                // publish PantryItemQuantityLimitCredited event
            } catch (e: PantryItemQuantityLimitExceeded) {
                // publish rejected event message
            }
        } catch (e: PantryItemNotFoundException) {
            // publish failed event message
        }
    }

    @Throws(PantryItemQuantityLimitExceeded::class)
    private fun PantryItem.creditQuantityLimit(orderQuantity: Int) {
        if (quantityLimit - orderQuantity >= 0)
            return
        else
            throw PantryItemQuantityLimitExceeded("Pantry item exceeded its quantity limit")
    }
}