package org.example.orders.web

import org.http4k.core.Body
import org.http4k.core.Response
import org.http4k.core.with
import org.http4k.format.Jackson.asJsonObject
import org.http4k.format.Jackson.json

data class PatchOrderRequest(val newPantryItemQuantity: Int)

fun Response.withError(reason: String): Response {
    val lens = Body.json().toLens()
    val error = object { val message = reason }.asJsonObject()
    return this.with(lens of error)
}