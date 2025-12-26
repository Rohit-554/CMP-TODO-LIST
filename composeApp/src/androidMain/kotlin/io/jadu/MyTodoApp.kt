package io.jadu

import android.app.Application
import io.jadu.todoApp.ui.notification.PlatformActivityProvider

class MyTodoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(PlatformActivityProvider)
        initKoin(this)
    }
}