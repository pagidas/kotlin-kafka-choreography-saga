package org.example

import org.example.web.OrderController
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.slf4j.LoggerFactory

fun main() {
    val log = LoggerFactory.getLogger("org.example.Application")
    log.info("Starting http4k app...")
    OrderController.routes.asServer(Jetty()).start()
}