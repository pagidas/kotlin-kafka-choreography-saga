package org.example.pantry.pubsub

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.example.avro.order.events.OrderEvent
import org.example.pantry.model.OrderEventType
import org.example.pantry.service.PantryService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.temporal.ChronoUnit

object OrderEventsConsumer {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }
    private const val ORDER_EVENTS_TOPIC = "order-events"

    private val pantryService = PantryService

    operator fun invoke() {
        val consumer = KafkaConsumer<String, OrderEvent>(consumerProperties).apply {
            subscribe(listOf(ORDER_EVENTS_TOPIC))
            log.info("Subscribed to topic: $ORDER_EVENTS_TOPIC")
        }

        consumer.use {
            while (true) {
                it.poll(Duration.of(100, ChronoUnit.MILLIS))
                    .filter { record -> record.value().type.contentEquals(OrderEventType.OrderCreated.name) }
                    .forEach { record ->
                    log.info("Consumed message ${record.value()}")
                    pantryService.creditItemQuantity(record.value())
                }
            }
        }
    }
}