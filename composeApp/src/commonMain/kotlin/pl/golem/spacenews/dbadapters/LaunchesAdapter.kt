package pl.golem.spacenews.dbadapters

import app.cash.sqldelight.ColumnAdapter
import kotlinx.serialization.json.Json
import pl.golem.spacenews.data.remote.Launch

val launchesAdapter = object : ColumnAdapter<List<Launch>, String> {

    override fun decode(databaseValue: String): List<Launch> {
        return if (databaseValue.isBlank())
            emptyList()
        else Json.decodeFromString(databaseValue)
    }

    override fun encode(value: List<Launch>): String {
        return Json.encodeToString(value)
    }

}