package org.example

import org.example.web.OrderController
import org.http4k.server.Jetty
import org.slf4j.LoggerFactory

fun main() {
    val log by lazy { LoggerFactory.getLogger("org.example.Application") }

    log.info("Starting http4k app...")
    Jetty().toServer(
        OrderController.routes
    ).start()
}