package org.example.orders.pubsub

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.example.avro.order.events.OrderEvent
import org.example.orders.model.Order
import org.example.orders.model.OrderEventType
import org.example.orders.model.OrderStatus
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object OrderEventsProducer {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }
    private const val ORDER_EVENTS_TOPIC = "order-events"

    private val producer = KafkaProducer<String, OrderEvent>(producerProperties)

    fun pushOrder(order: Order) {
        val event = when(order.orderStatus) {
            OrderStatus.PENDING -> OrderEvent(
                OrderEventType.OrderCreated.name,
                order.id.toString(),
                order.orderStatus.name,
                order.pantryItemId.toString(),
                order.quantity)
            OrderStatus.REJECTED -> OrderEvent(
                OrderEventType.OrderRejected.name,
                order.id.toString(),
                order.orderStatus.name,
                order.pantryItemId.toString(),
                order.quantity)
            else -> return
        }
        try {
            log.info("Pushing to topic: $ORDER_EVENTS_TOPIC message: $event")
            producer.send(ProducerRecord(ORDER_EVENTS_TOPIC, event.orderId, event))
        } catch (e: Exception) {
            log.error("Error pushing event: $event")
        }
    }
}