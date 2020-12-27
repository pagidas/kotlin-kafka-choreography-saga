package org.example.pantry

import org.example.pantry.pubsub.OrderEventsConsumer
import org.example.pantry.web.PantryController
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.slf4j.LoggerFactory

fun main() {
    val log = LoggerFactory.getLogger("org.example.pantry.Application")
    log.info("Starting pantry app...")
    PantryController.routes.asServer(Jetty(8080)).start()
    OrderEventsConsumer
}