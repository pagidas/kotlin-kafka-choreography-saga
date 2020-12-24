package org.example.pantry.pubsub

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.example.avro.order.events.OrderEvent
import org.example.avro.pantry.events.PantryEvent
import org.example.pantry.exceptions.PantryItemNotFoundException
import org.example.pantry.exceptions.PantryItemQuantityLimitExceeded
import org.example.pantry.model.PantryEventType
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object PantryEventsProducer {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }
    private const val PANTRY_EVENTS_TOPIC = "pantry-events"

    private val producer = KafkaProducer<String, PantryEvent>(producerProperties)

    fun handleOrderCreatedEvent(orderCreatedEvent: OrderEvent, e: RuntimeException? = null) {
        val event: PantryEvent = e?.let {
            // failing/rejection scenarios
            when(e) {
                is PantryItemNotFoundException -> PantryEvent(
                    PantryEventType.PantryItemQuantityLimitFailed.name,
                    orderCreatedEvent.pantryItemId,
                    orderCreatedEvent.quantity
                )
                is PantryItemQuantityLimitExceeded -> PantryEvent(
                    PantryEventType.PantryItemQuantityLimitRejected.name,
                    orderCreatedEvent.pantryItemId,
                    orderCreatedEvent.quantity
                )
                else -> PantryEvent(
                    PantryEventType.PantryItemQuantityLimitFailed.name,
                    orderCreatedEvent.pantryItemId,
                    orderCreatedEvent.quantity
                )
            }
        } ?: PantryEvent( // successful scenario
            PantryEventType.PantryItemQuantityLimitCredited.name,
            orderCreatedEvent.pantryItemId,
            orderCreatedEvent.quantity)
        try {
            log.info("Pushing to topic: $PANTRY_EVENTS_TOPIC message: $event")
            producer.send(ProducerRecord(PANTRY_EVENTS_TOPIC, event.pantryItemId, event))
        } catch (e: Exception) {
            log.error("Error pushing event: $event")
        }
    }
}