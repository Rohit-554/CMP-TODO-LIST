package io.jadu

import createDataStore
import getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

val iosModule = module {
    single { getDatabaseBuilder() }
    single { createDataStore() }
}
actual fun platformModule(): Module = iosModule

fun doInitKoin() {
    initKoin()
}