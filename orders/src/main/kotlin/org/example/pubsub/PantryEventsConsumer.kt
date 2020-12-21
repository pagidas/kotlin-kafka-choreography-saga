package org.example.pubsub

import org.apache.kafka.clients.consumer.KafkaConsumer
import org.example.avro.order.events.OrderEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.temporal.ChronoUnit

object PantryEventsConsumer {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }
    private const val PANTRY_EVENTS_TOPIC = "pantry-events"

    operator fun invoke() {
        val consumer = KafkaConsumer<String, OrderEvent>(consumerProperties).apply {
            subscribe(listOf(PANTRY_EVENTS_TOPIC))
            log.info("Subscribed to topic: $PANTRY_EVENTS_TOPIC")
        }

        consumer.use {
            while (true) {
                it.poll(Duration.of(100, ChronoUnit.MILLIS)).forEach { record ->
                    log.debug("Offset = ${record.offset()}, key = ${record.key()}, value = ${record.value()}")
                }
                consumer.commitAsync()
            }
        }
    }
}