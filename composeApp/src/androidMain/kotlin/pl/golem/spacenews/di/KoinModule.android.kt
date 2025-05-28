package pl.golem.spacenews.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import pl.golem.spacenews.AndroidDatabaseDriverFactory
import pl.golem.spacenews.data.local.DatabaseDriver

actual val targetModule = module {
    single<DatabaseDriver> {
        AndroidDatabaseDriverFactory(androidContext())
    }
}