package org.example.pantry

import org.example.pantry.pubsub.OrderEventsConsumer
import org.slf4j.LoggerFactory

fun main() {
    val log = LoggerFactory.getLogger("org.example.pantry.Application")
    log.info("Starting http4k app...")
    OrderEventsConsumer()
}