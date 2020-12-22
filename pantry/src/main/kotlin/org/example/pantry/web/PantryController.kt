package org.example.pantry.web

import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
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
        "/" bind Method.POST to { _: Request ->
            Response(Status.OK)
        }
}