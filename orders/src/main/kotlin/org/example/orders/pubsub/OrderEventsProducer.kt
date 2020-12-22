package org.example.orders.pubsub

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.example.avro.order.events.OrderEvent
import org.example.orders.model.Order
import org.example.orders.model.OrderEventStatus
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object OrderEventsProducer {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }
    private const val ORDER_EVENTS_TOPIC = "order-events"

    private val producer = KafkaProducer<String, OrderEvent>(producerProperties)

    fun createOrder(order: Order) {
        val event = OrderEvent(OrderEventStatus.OrderCreated.name, order.id.toString(), order.orderStatus.name)
        try {
            log.info("Pushing to topic: $ORDER_EVENTS_TOPIC message: $event")
            producer.send(ProducerRecord(ORDER_EVENTS_TOPIC, event.orderId, event))
        } catch (e: Exception) {
            log.error("Error pushing event: $event")
        }
    }
}