package org.example.pantry.service

import org.example.avro.order.events.OrderEvent
import org.example.pantry.exceptions.PantryItemQuantityLimitExceeded
import org.example.pantry.model.PantryItem
import org.example.pantry.pubsub.PantryEventsProducer
import org.example.pantry.repository.PantryRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

object PantryService {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }

    private val pantryRepo = PantryRepository
    private val pantryProducer = PantryEventsProducer

    fun creditItemQuantity(createdOrderEvent: OrderEvent) {
        log.info("Attempt to credit item quantity")
        try {
            val item = pantryRepo.selectPantryItemById(UUID.fromString(createdOrderEvent.pantryItemId))
            item.checkQuantityLimit(createdOrderEvent.quantity)
            pantryRepo.updateQuantityLimit(
                UUID.fromString(createdOrderEvent.pantryItemId),
                createdOrderEvent.quantity)
            pantryProducer.handleOrderCreatedEvent(createdOrderEvent)
        } catch (e: RuntimeException) {
            pantryProducer.handleOrderCreatedEvent(createdOrderEvent, e)
        }
    }

    @Throws(PantryItemQuantityLimitExceeded::class)
    private fun PantryItem.checkQuantityLimit(orderQuantity: Int) {
        if (quantityLimit - orderQuantity >= 0)
            return
        else
            throw PantryItemQuantityLimitExceeded("Pantry item exceeded its quantity limit")
    }
}