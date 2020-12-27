package org.example.pantry.repository

import org.example.pantry.exceptions.PantryItemNotFoundException
import org.example.pantry.model.PantryItem
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import kotlin.math.absoluteValue

object PantryRepository {
    private val log: Logger by lazy { LoggerFactory.getLogger(this::class.java) }

    private val dbConnection = PantryDatabase.connection

    @Throws(PantryItemNotFoundException::class)
    fun selectPantryItemById(id: UUID): PantryItem {
        log.info("Attempt to select pantry item by id")
        val statement = dbConnection.createStatement()
        val result = statement.executeQuery("""
            select * from pantry_app.items where id='$id'
            """.trimIndent())

        if (result.next())
            return PantryItem(
                result.getObject("id", UUID::class.java),
                result.getString("name"),
                result.getInt("quantity_limit"))
            else
                throw PantryItemNotFoundException("Pantry Item by id: $id not found in the database")
    }

    fun updateQuantityLimit(pantryItemId: UUID, quantity: Int) {
        log.info("Attempt to update pantry item quantity limit")
        dbConnection.createStatement().executeUpdate("""
            update pantry_app.items
            set quantity_limit = quantity_limit - $quantity
            where id='$pantryItemId'
        """.trimIndent())
    }

    fun insertItem(pantryItem: PantryItem): PantryItem {
        log.info("Attempt to insert pantry item")
        val result = dbConnection.createStatement().executeQuery(
            """
            insert into pantry_app.items 
            values ('${pantryItem.id}', '${pantryItem.name}', '${pantryItem.quantityLimit}')
            returning *
        """.trimIndent())
        result.next()

        return PantryItem(
            result.getObject("id", UUID::class.java),
            result.getString("name"),
            result.getInt("quantity_limit")
        )
    }
}