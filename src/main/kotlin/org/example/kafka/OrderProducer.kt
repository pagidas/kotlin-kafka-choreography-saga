package org.example.kafka

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.example.avro.order.events.OrderEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object OrderProducer {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }
    private const val ORDER_EVENTS_TOPIC = "order-events"

    private val producer = KafkaProducer<String, OrderEvent>(properties)

    fun createOrder(event: OrderEvent) {
        try {
            log.info("Pushing to topic: $ORDER_EVENTS_TOPIC message: $event")
            producer.send(ProducerRecord(ORDER_EVENTS_TOPIC, event.orderId, event)).get()
        } catch (e: Exception) {
            log.error("Error pushing event: $event")
        }
    }
}