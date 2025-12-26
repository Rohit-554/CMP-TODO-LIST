package io.jadu.todoApp.ui.notification

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference

object PlatformActivityProvider : Application.ActivityLifecycleCallbacks {
    // WeakReference prevents memory leaks
    private var currentActivityRef: WeakReference<Activity>? = null

    val currentActivity: Activity?
        get() = currentActivityRef?.get()


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
    override fun onActivityStarted(activity: Activity) {
        currentActivityRef = WeakReference(activity)
    }
    override fun onActivityResumed(activity: Activity) {
        currentActivityRef = WeakReference(activity)
    }
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}
}