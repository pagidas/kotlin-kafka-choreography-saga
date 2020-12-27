package org.example.orders.web

import org.example.orders.model.Order
import org.example.orders.model.OrderStatus
import org.example.orders.service.OrderService
import org.http4k.core.*
import org.http4k.format.Jackson.auto
import org.http4k.lens.Path
import org.http4k.lens.uuid
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

object OrderController {

    private val orderService = OrderService

    val routes: RoutingHttpHandler by lazy {
        "/orders" bind routes(
            createOrder,
            reOrder
        )
    }

    private val createOrder: RoutingHttpHandler =
        "/" bind Method.POST to { req: Request ->
            val lens = Body.auto<Order>().toLens()
            val order = lens(req)
            val created = orderService.createOrder(order)
            if (created.status == OrderStatus.FAILED)
                Response(Status.NOT_FOUND).withError("Pantry item id not found")
            else
                Response(Status.CREATED).with(lens of created)
        }

    private val reOrder: RoutingHttpHandler =
        "/{id}" bind Method.PATCH to { req: Request ->
            val lens = Body.auto<PatchOrderRequest>().toLens()
            val pathVarLens = Path.uuid().of("id")

            val patchId = pathVarLens(req)
            val patch = lens(req)
            orderService.updateOrderQuantity(patchId, patch)
            Response(Status.NO_CONTENT)
        }
}
