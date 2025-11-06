package io.jadu


import io.jadu.todoApp.data.TodoRepositoryImpl
import io.jadu.todoApp.domain.TodoRepository
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

private var isKoinInitialized = false

val appModule = module {
    single<TodoRepository> { TodoRepositoryImpl() }
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