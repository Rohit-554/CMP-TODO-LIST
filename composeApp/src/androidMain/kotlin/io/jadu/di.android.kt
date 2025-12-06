package io.jadu

import android.app.Application
import createDataStore
import getDatabaseBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val androidModule = module {
    single { getDatabaseBuilder(androidContext()) }
    single { createDataStore(androidContext()) }
}

actual fun platformModule(): Module = androidModule

fun initKoin(application: Application) {
    initKoin { koinApplication ->
        koinApplication.apply {
            androidContext(application)
        }
    }
}