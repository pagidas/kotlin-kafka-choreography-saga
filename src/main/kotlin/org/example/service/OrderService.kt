package org.example.service

import org.example.model.Order
import org.example.pubsub.OrderEventsProducer
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object OrderService {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }

    private val orderProducer = OrderEventsProducer

    init {
        log.info("Initialised $this...")
    }

    fun createOrder(order: Order): Order {
        // save order
        orderProducer.createOrder(order)
        return order
    }
}