package nl.bryanderidder.ornaguide

import android.app.Application
import nl.bryanderidder.ornaguide.shared.di.appModule
import nl.bryanderidder.ornaguide.shared.logging.CrashlyticsTree
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

/**
 * Main Application
 * @author Bryan de Ridder
 */
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //Start Timber (logging)
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
        else
            Timber.plant(CrashlyticsTree())

        // start Koin context
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}