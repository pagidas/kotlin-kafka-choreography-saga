package org.example.pantry.web

import org.example.pantry.model.PantryItem
import org.http4k.core.*
import org.http4k.format.Jackson.auto
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object PantryController {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }

    private val routes: RoutingHttpHandler by lazy {
        "/pantry" bind routes(
            createItem
        )
    }

    private val createItem: RoutingHttpHandler =
        "/" bind Method.POST to { req: Request ->
            val requestLens = Body.auto<CreatePantryItemRequest>().toLens()
            val responseLens = Body.auto<PantryItem>().toLens()

            val payload = requestLens(req)
            val pantryItem = PantryItem(name = payload.name, quantityLimit = payload.quantityLimit)
            // call service

            Response(Status.CREATED).with(responseLens of pantryItem)
        }
}