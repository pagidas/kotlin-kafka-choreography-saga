package org.example.orders.service

import org.example.avro.pantry.events.PantryEvent
import org.example.orders.model.Order
import org.example.orders.model.OrderStatus
import org.example.orders.pubsub.OrderEventsProducer
import org.example.orders.repository.OrderRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

object OrderService {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }

    private val orderRepo = OrderRepository
    private val orderProducer = OrderEventsProducer

    fun createOrder(order: Order): Order {
        log.info("Attempt to create a new order")
        try {
            orderRepo.insertOrder(order)
            orderProducer.pushOrder(order)
        } catch (e: RuntimeException) {
            val failedOrder = order.copy(status = OrderStatus.FAILED)
            orderProducer.pushOrder(failedOrder)
            return failedOrder
        }
        return order
    }

    fun approveOrder(pantryEvent: PantryEvent) {
        log.info("Attempt to approve the order")
        val updated = orderRepo.updateOrderStatus(UUID.fromString(pantryEvent.orderId), OrderStatus.APPROVED)
        orderProducer.pushOrder(updated)
    }

    fun rejectOrder(pantryEvent: PantryEvent) {
        log.info("Attempt to reject the order")
        val updated = orderRepo.updateOrderStatus(UUID.fromString(pantryEvent.orderId), OrderStatus.REJECTED)
        orderProducer.pushOrder(updated)
    }

    fun failOrder(pantryEvent: PantryEvent) {
        log.info("Attempt to fail the order")
        val updated = orderRepo.updateOrderStatus(UUID.fromString(pantryEvent.orderId), OrderStatus.FAILED)
        orderProducer.pushOrder(updated)
    }
}