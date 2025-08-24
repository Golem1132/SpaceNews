package pl.golem.spacenews.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import pl.golem.spacenews.Result
import pl.golem.spacenews.api.ArticlesService
import pl.golem.spacenews.api.KtorClient
import pl.golem.spacenews.data.local.DatabaseDriver
import pl.golem.spacenews.dbadapters.authorsAdapter
import pl.golem.spacenews.dbadapters.eventsAdapter
import pl.golem.spacenews.dbadapters.launchesAdapter
import pl.golem.spacenews.navigation.Blogs
import pl.golem.spacenews.screen.articles.ArticlesRepository
import pl.golem.spacenews.screen.articles.ArticlesViewModel
import pl.golem.spacenews.screen.blogs.BlogsRepository
import pl.golem.spacenews.screen.blogs.BlogsViewModel
import pl.golem.spacenews.screen.reports.ReportsRepository
import pl.golem.spacenews.screen.reports.ReportsViewModel
import pl.golem.spacenews.sqldelight.SpaceNewsDatabase


expect val targetModule: Module
val sharedModule = module {

    single {
        get<DatabaseDriver>().createDriver()
    }

    single {
        SpaceNewsDatabase(
            get(), Result.Adapter(
                authorsAdapter = authorsAdapter,
                launchesAdapter = launchesAdapter,
                eventsAdapter = eventsAdapter
            )
        )
    }
    single {
        get<SpaceNewsDatabase>().resultQueries
    }

    single {
        get<SpaceNewsDatabase>().remoteKeysQueries
    }

    single {
        KtorClient()
    }

    single {
        get<KtorClient>().articlesService
    }

    single {
        get<KtorClient>().blogsService
    }

    single {
        get<KtorClient>().reportsService
    }

    single {
        ArticlesRepository(
            get(),
            get(),
            get()
        )
    }

    single {
        BlogsRepository(
            get(),
            get(),
            get()
        )
    }

    single {
        ReportsRepository(
            get(),
            get(),
            get()
        )
    }

    viewModel {
        ArticlesViewModel(get())
    }

    viewModel {
        BlogsViewModel(get())
    }

    viewModel {
        ReportsViewModel(get())
    }
}

fun initializeKoin(config: (KoinApplication.() -> Unit)? = null) {
    startKoin {
        config?.invoke(this)
        modules(targetModule, sharedModule)
    }
}