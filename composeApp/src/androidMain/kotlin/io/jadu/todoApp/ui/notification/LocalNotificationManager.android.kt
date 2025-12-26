package io.jadu.todoApp.ui.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import kotlin.random.Random

actual class LocalNotificationManager (
    private val context: Context
) {
    actual fun showNotification(title: String, body: String) {
        val channelId = "default_channel_id"
        val notificationId = Random.nextInt()

        // 1. Create the NotificationChannel (Required for API 26+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "General Notifications"
            val descriptionText = "Default channel for app notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // 2. Build the notification
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Replace with your app's icon
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        // 3. Show the notification
        with(NotificationManagerCompat.from(context)) {
            // Check for permission strictly required on Android 13+
            try {
                notify(notificationId, builder.build())
            } catch (e: SecurityException) {
                // Handle missing permission
                println("Notification permission missing")
            }
        }
    }

    actual fun hasPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= 33) { // Android 13+
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true // Android 12 and below have implicit permission
        }
    }

    actual suspend fun requestPermission(): Boolean {
        if (hasPermission()) return true

        // for Android 13
        if (Build.VERSION.SDK_INT >= 33) {
            val activity = PlatformActivityProvider.currentActivity
            if (activity!=null) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    123 // Request Code
                )
                return false
            } else {
                println("LocalNotificationService needs an Activity Context to request permissions")
                return false
            }
        }
        return true
    }
}