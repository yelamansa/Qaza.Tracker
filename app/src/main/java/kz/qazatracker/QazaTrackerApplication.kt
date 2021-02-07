package kz.qazatracker

import android.app.Application
import kz.qazatracker.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class QazaTrackerApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(applicationModule)
        }
    }
}