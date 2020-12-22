package org.example.orders.service

import org.example.orders.model.Order
import org.example.orders.pubsub.OrderEventsProducer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object OrderService {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }

    private val orderProducer = OrderEventsProducer

    fun createOrder(order: Order): Order {
        // save order
        orderProducer.createOrder(order)
        return order
    }
}