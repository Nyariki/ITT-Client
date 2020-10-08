package com.itt.client

import androidx.multidex.MultiDexApplication
import com.itt.client.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class AppController : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        // start Koin context
        startKoin {
            // Use Android Context from MainApplication
            androidContext(this@AppController)
            // Load modules
            modules(appModules)
        }

        instance = this
    }

    companion object {
        @JvmStatic
        @get:Synchronized
        var instance: AppController? = null
            private set

    }

}