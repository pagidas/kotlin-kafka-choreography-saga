package org.example.web

import org.example.model.Order
import org.example.service.OrderService
import org.http4k.core.*
import org.http4k.format.Jackson.auto
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object OrderController {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }

    private val orderService = OrderService

    val routes: RoutingHttpHandler by lazy {
        "/orders" bind routes(
            createOrder
        )
    }

    private val createOrder: RoutingHttpHandler =
        "/" bind Method.POST to { _: Request ->
            val lens = Body.auto<Order>().toLens()
            val created = orderService.createOrder(Order())
            Response(Status.CREATED).with(lens of created)
        }
}
