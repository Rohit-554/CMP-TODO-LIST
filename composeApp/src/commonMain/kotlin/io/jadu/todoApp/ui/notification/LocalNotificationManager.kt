package io.jadu.todoApp.ui.notification

expect class LocalNotificationManager {
    fun hasPermission(): Boolean
    suspend fun requestPermission(): Boolean
    fun showNotification(title: String, body: String)
}