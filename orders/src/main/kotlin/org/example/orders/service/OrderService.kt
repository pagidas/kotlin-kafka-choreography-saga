package org.example.orders.service

import org.example.orders.exceptions.PantryItemNotFoundException
import org.example.orders.model.Order
import org.example.orders.model.OrderStatus
import org.example.orders.pubsub.OrderEventsProducer
import org.example.orders.repository.OrderRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.sql.Connection

object OrderService {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }

    private val orderRepo = OrderRepository
    private val orderProducer = OrderEventsProducer

    fun createOrder(order: Order): Order {
        log.info("Attempt to create a new order")
        try {
            orderRepo.insertOrder(order)
            orderProducer.createOrder(order)
        } catch (e: PantryItemNotFoundException) {
            val rejectedOrder = order.copy(orderStatus = OrderStatus.REJECTED)
            orderProducer.createOrder(rejectedOrder)
            return rejectedOrder
        }
        return order
    }
}