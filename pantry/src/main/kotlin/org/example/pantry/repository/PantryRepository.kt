package org.example.pantry.repository

import org.example.pantry.exceptions.PantryItemNotFoundException
import org.example.pantry.model.PantryItem
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import kotlin.jvm.Throws

object PantryRepository {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }

    private val dbConnection = PantryDatabase.connection

    @Throws(PantryItemNotFoundException::class)
    fun selectPantryItemById(id: UUID): PantryItem {
        log.info("Attempt to select pantry item")
        val statement = dbConnection.createStatement()
        val result = statement.executeQuery("""
            select all from pantry_app.items where id='$id' 
        """.trimIndent())

        if (result.next())
            return PantryItem(
                UUID.fromString(result.getString("id")),
                result.getString("name"),
                result.getInt("quantity_limit")
            )
        throw PantryItemNotFoundException("Pantry Item by id: $id not found in the database")
    }
}