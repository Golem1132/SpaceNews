package pl.golem.spacenews.dbadapters

import app.cash.sqldelight.ColumnAdapter
import kotlinx.serialization.json.Json
import pl.golem.spacenews.data.remote.Author

val authorsAdapter = object : ColumnAdapter<List<Author>, String> {
    override fun decode(databaseValue: String): List<Author> {
        return if (databaseValue.isBlank())
            emptyList()
        else Json.decodeFromString<List<Author>>(databaseValue)

    }

    override fun encode(value: List<Author>): String {
        return Json.encodeToString(value)
    }
}