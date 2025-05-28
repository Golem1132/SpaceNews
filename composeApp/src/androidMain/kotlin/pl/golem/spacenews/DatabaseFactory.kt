package pl.golem.spacenews

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import pl.golem.spacenews.data.local.DatabaseDriver
import pl.golem.spacenews.sqldelight.SpaceNewsDatabase

class AndroidDatabaseDriverFactory(
    private val context: Context
): DatabaseDriver {

    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(SpaceNewsDatabase.Schema, context, "SpaceNewsDatabase.db")
    }
}