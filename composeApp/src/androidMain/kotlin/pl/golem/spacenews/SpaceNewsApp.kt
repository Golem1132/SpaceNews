package pl.golem.spacenews

import android.app.Application
import org.koin.android.ext.koin.androidContext
import pl.golem.spacenews.di.initializeKoin

class SpaceNewsApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin(
            config = { androidContext(this@SpaceNewsApp)}
        )
    }
}