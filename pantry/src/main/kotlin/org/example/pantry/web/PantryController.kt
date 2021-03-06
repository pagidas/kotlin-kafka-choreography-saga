package org.example.pantry.web

import org.example.pantry.model.PantryItem
import org.example.pantry.service.PantryService
import org.http4k.core.*
import org.http4k.format.Jackson.auto
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.routing.routes

object PantryController {

    private val pantryService = PantryService

    val routes: RoutingHttpHandler by lazy {
        "/pantry-items" bind routes(
            createItem
        )
    }

    private val createItem: RoutingHttpHandler =
        "/" bind Method.POST to { req: Request ->
            val requestLens = Body.auto<CreatePantryItemRequest>().toLens()
            val responseLens = Body.auto<PantryItem>().toLens()

            val payload = requestLens(req)
            val pantryItem = PantryItem(name = payload.name, quantityLimit = payload.quantityLimit)
            val created = pantryService.createItem(pantryItem)

            Response(Status.CREATED).with(responseLens of created)
        }
}