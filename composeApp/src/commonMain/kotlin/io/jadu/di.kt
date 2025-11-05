package io.jadu


import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

private var isKoinInitialized = false

val appModule = module {
    single {
        //setup viewmodel or classes
    }
    factory {  } //
}


fun initKoin() {
    if (!isKoinInitialized) {
        startKoin {
            modules(appModule)
        }
        isKoinInitialized = true
    }
}


fun resetKoin() {
    stopKoin()
    isKoinInitialized = false
    initKoin()
}