package io.jadu

import createDataStore
import getDatabaseBuilder
import io.jadu.todoApp.ui.notification.LocalNotificationManager
import org.koin.core.module.Module
import org.koin.dsl.module

val iosModule = module {
    single { getDatabaseBuilder() }
    single { createDataStore() }
    single { LocalNotificationManager() }
}
actual fun platformModule(): Module = iosModule

fun doInitKoin() {
    initKoin()
}