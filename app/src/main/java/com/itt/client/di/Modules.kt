package com.itt.client.di

import android.content.Context
import com.google.gson.Gson
import com.itt.client.R
import com.itt.client.createOkHttpClient
import com.itt.client.createWebService
import com.itt.client.data.remote.RestRequests
import com.itt.client.data.repository.RestRepository
import com.itt.client.view.main.MainActivityViewModel
import com.itt.client.view.report.ReportActivityViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin main module
 */
val MainAppModule = module {
    // Gson
    single { Gson() }

}

/**
 * Data module
 */
val DataModule = module {
    // RestRepository instance
    single { RestRepository(get()) }

}

/**
 * ViewModels module
 */
val ViewModelsModule = module {
    //Provides an instance of ViewModel and binds it to an Android Component lifecycle
    viewModel { MainActivityViewModel(get()) }
    viewModel { ReportActivityViewModel(get()) }

}

/**
 * Network module
 */
val NetworkModule = module {
    //REST service
    single {
        createWebService<RestRequests>(
            createOkHttpClient(false),
            get<Context>().getString(R.string.app_url)
        )
    }
}


/**
 * Module list
 */
val appModules = listOf(MainAppModule, ViewModelsModule, NetworkModule, DataModule)


