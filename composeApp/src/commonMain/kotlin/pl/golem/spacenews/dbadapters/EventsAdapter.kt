package pl.golem.spacenews.dbadapters

import app.cash.sqldelight.ColumnAdapter
import kotlinx.serialization.json.Json
import pl.golem.spacenews.data.remote.Event

val eventsAdapter = object : ColumnAdapter<List<Event>, String> {
    override fun decode(databaseValue: String): List<Event> {
        return if (databaseValue.isBlank())
            emptyList()
        else Json.decodeFromString(databaseValue)
    }

    override fun encode(value: List<Event>): String {
        return Json.encodeToString(value)
    }

}