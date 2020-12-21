package org.example.web

import org.http4k.core.Method
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.slf4j.LoggerFactory

object OrderController {
    private val log by lazy { LoggerFactory.getLogger(this::class.java) }

    init {
        log.info("Initialising $this...")
    }

    val routes: RoutingHttpHandler by lazy {
        "/orders" bind routes(
            createOrder()
        )
    }

    private fun createOrder(): RoutingHttpHandler =
        "/" bind Method.POST to { req ->
            Response(Status.OK)
        }
}
