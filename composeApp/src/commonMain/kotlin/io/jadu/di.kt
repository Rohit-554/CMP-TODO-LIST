package io.jadu


import androidx.room.RoomDatabase
import io.jadu.todoApp.data.TodoRepositoryImpl
import io.jadu.todoApp.data.local.AppDatabase
import io.jadu.todoApp.domain.TodoRepository
import io.jadu.todoApp.ui.notification.NotificationViewModel
import io.jadu.todoApp.ui.viewModel.AddProjectViewModel
import io.jadu.todoApp.ui.viewModel.EditTodoViewModel
import io.jadu.todoApp.ui.viewModel.HomeScreenViewModel
import io.jadu.todoApp.ui.viewModel.MostUsedCategoryViewModel
import io.jadu.todoApp.ui.viewModel.OnBoardingViewModel
import io.jadu.todoApp.ui.viewModel.SettingsViewModel
import io.jadu.todoApp.ui.viewModel.TaskScreenViewModel
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module

private var koinRef: Koin? = null

fun initKoin(config: (KoinApplication) -> Unit = {}) {
  if(koinRef == null) {
      val app = startKoin {
          config(this)
          modules(appModule, platformModule())
      }
      koinRef = app.koin
  }
}

val appModule = module {
    single { get<RoomDatabase.Builder<AppDatabase>>().build() }
    single { get<AppDatabase>().getDao() }
    single { get<AppDatabase>().getUserProfileDao() }
    single { OnBoardingViewModel(get()) }
    single<TodoRepository> { TodoRepositoryImpl() }
    single { AddProjectViewModel(get()) }
    single { EditTodoViewModel(get()) }
    single { TaskScreenViewModel(get()) }
    single { HomeScreenViewModel(get(), get()) }
    single { SettingsViewModel(get(), get()) }
    single { MostUsedCategoryViewModel(get()) }
    single { NotificationViewModel(get()) }
}


fun resetKoin() {
    stopKoin()
    koinRef = null
    initKoin()
}

expect fun platformModule() : Module

fun getKoin(): Koin =
    koinRef ?: throw Throwable("Koin is not initialized. Call initKoin() first.")