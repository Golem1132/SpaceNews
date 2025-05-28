package pl.golem.spacenews

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import pl.golem.spacenews.data.local.DatabaseDriver
import pl.golem.spacenews.sqldelight.SpaceNewsDatabase

class IOSDatabaseDriverFactory: DatabaseDriver {

    override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(SpaceNewsDatabase.Schema, "SpaceNewsDatabase.db")
    }
}