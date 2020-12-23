package org.example.orders.service

import org.example.orders.model.Order
import org.example.orders.pubsub.OrderEventsProducer
import org.example.orders.repository.OrderRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object OrderService {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }

    private val orderRepo = OrderRepository
    private val orderProducer = OrderEventsProducer

    fun createOrder(order: Order): Order {
        log.info("Attempt to create a new order")
        orderRepo.insertOrder(order)
        orderProducer.createOrder(order)
        return order
    }
}