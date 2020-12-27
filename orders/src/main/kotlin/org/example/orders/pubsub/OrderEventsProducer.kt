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
        val event = when(order.status) {
            OrderStatus.PENDING -> orderPendingToOrderCreatedEvent(order)
            OrderStatus.REJECTED -> orderRejectedToOrderRejectedEvent(order)
            OrderStatus.APPROVED -> orderApprovedToOrderApprovedEvent(order)
            OrderStatus.RETRY -> orderRetryToOrderUpdatedEvent(order)
            else -> return
        }
        try {
            log.info("Pushing to topic: $ORDER_EVENTS_TOPIC message: $event")
            producer.send(ProducerRecord(ORDER_EVENTS_TOPIC, event.orderId, event))
        } catch (e: Exception) {
            log.error("Error pushing event: $event")
        }
    }

    private val orderPendingToOrderCreatedEvent: (Order) -> OrderEvent = { o ->
        OrderEvent(
            OrderEventType.OrderCreated.name,
            o.id.toString(),
            o.status.name,
            o.pantryItemId.toString(),
            o.pantryItemQuantity
        )
    }

    private val orderRejectedToOrderRejectedEvent: (Order) -> OrderEvent = { o ->
        OrderEvent(
            OrderEventType.OrderRejected.name,
            o.id.toString(),
            o.status.name,
            o.pantryItemId.toString(),
            o.pantryItemQuantity)
    }

    private val orderApprovedToOrderApprovedEvent: (Order) -> OrderEvent = { o ->
        OrderEvent(
            OrderEventType.OrderApproved.name,
            o.id.toString(),
            o.status.name,
            o.pantryItemId.toString(),
            o.pantryItemQuantity)
    }

    private val orderRetryToOrderUpdatedEvent: (Order) -> OrderEvent = { o ->
        OrderEvent(
            OrderEventType.OrderUpdated.name,
            o.id.toString(),
            o.status.name,
            o.pantryItemId.toString(),
            o.pantryItemQuantity)
    }
}