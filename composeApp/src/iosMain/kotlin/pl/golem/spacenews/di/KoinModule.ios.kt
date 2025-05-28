package pl.golem.spacenews.di

import org.koin.core.module.Module
import org.koin.dsl.module
import pl.golem.spacenews.IOSDatabaseDriverFactory
import pl.golem.spacenews.data.local.DatabaseDriver

actual val targetModule = module {
    single<DatabaseDriver> {
        IOSDatabaseDriverFactory()
    }
}