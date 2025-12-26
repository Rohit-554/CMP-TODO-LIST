package io.jadu.todoApp.ui.notification

import platform.Foundation.timeIntervalSince1970
import platform.UserNotifications.UNAuthorizationOptionAlert
import platform.UserNotifications.UNAuthorizationOptionBadge
import platform.UserNotifications.UNAuthorizationOptionSound
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNTimeIntervalNotificationTrigger
import platform.UserNotifications.UNUserNotificationCenter
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class LocalNotificationManager {

    init {
        // Request permission on initialization (or call this from a separate method)
        val center = UNUserNotificationCenter.currentNotificationCenter()
        val options = UNAuthorizationOptionAlert or UNAuthorizationOptionSound or UNAuthorizationOptionBadge

        center.requestAuthorizationWithOptions(options) { granted, error ->
            if (granted) {
                println("Notification permission granted")
            } else {
                println("Notification permission denied: $error")
            }
        }
    }

    actual fun showNotification(title: String, body: String) {
        val content = UNMutableNotificationContent().apply {
            setTitle(title)
            setBody(body)
            setSound(platform.UserNotifications.UNNotificationSound.defaultSound)
        }

        // Trigger the notification after 1 second
        val trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(1.0, repeats = false)

        val request = UNNotificationRequest.requestWithIdentifier(
            identifier = "notification_${platform.Foundation.NSDate().timeIntervalSince1970}",
            content = content,
            trigger = trigger
        )

        UNUserNotificationCenter.currentNotificationCenter().addNotificationRequest(request) { error ->
            if (error != null) {
                println("Error showing notification: $error")
            }
        }
    }

    actual fun hasPermission(): Boolean {
       return true
    }

    actual suspend fun requestPermission(): Boolean = suspendCoroutine { continuation ->
        val center = UNUserNotificationCenter.currentNotificationCenter()
        val options = UNAuthorizationOptionAlert or UNAuthorizationOptionSound or UNAuthorizationOptionBadge

        center.requestAuthorizationWithOptions(options) { granted, error ->
            if (error != null) {
                println("Notification permission error: $error")
                continuation.resume(false)
            } else {
                continuation.resume(granted)
            }
        }
    }
}