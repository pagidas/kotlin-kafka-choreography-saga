package org.example.orders.pubsub

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.example.avro.pantry.events.PantryEvent
import org.example.orders.model.PantryEventType
import org.example.orders.service.OrderService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.temporal.ChronoUnit

object PantryEventsConsumer {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }
    private const val PANTRY_EVENTS_TOPIC = "pantry-events"

    private val orderService = OrderService

    init {
        val consumer = KafkaConsumer<String, PantryEvent>(consumerProperties).apply {
            subscribe(listOf(PANTRY_EVENTS_TOPIC))
            log.info("Subscribed to topic: $PANTRY_EVENTS_TOPIC")
        }

        consumer.use {
            while (true) {
                it.poll(Duration.of(100, ChronoUnit.MILLIS)).forEach { record ->
                    log.info("Consumed message ${record.value()}")
                    when(record.value().type) {
                        PantryEventType.PantryItemQuantityLimitCredited.name -> orderService.approveOrder(record.value())
                        PantryEventType.PantryItemQuantityLimitRejected.name -> orderService.rejectOrder(record.value())
                        PantryEventType.PantryItemQuantityLimitFailed.name -> orderService.failOrder(record.value())
                    }
                }
            }
        }
    }
}