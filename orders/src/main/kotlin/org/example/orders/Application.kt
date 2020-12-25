package org.example.orders

import org.example.orders.pubsub.PantryEventsConsumer
import org.example.orders.web.OrderController
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.slf4j.LoggerFactory

fun main() {
    val log = LoggerFactory.getLogger("org.example.orders.Application")
    log.info("Starting http4k app...")
    OrderController.routes.asServer(Jetty()).start()
    PantryEventsConsumer
}