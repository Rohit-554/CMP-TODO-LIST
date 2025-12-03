package io.jadu

import getDatabaseBuilder
import org.koin.core.module.Module
import org.koin.dsl.module

val iosModule = module {
    single {
        getDatabaseBuilder()
    }
}
actual fun platformModule(): Module = iosModule

fun doInitKoin() {
    initKoin()
}