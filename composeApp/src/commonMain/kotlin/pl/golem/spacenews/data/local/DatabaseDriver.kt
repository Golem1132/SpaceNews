package pl.golem.spacenews.data.local

import app.cash.sqldelight.db.SqlDriver



interface DatabaseDriver {
    fun createDriver(): SqlDriver
}