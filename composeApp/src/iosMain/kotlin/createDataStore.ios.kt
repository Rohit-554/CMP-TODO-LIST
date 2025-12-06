@file:OptIn(ExperimentalForeignApi::class)

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.jadu.todoApp.data.local.DATA_STORE_FILE_NAME
import io.jadu.todoApp.data.local.createDataStore
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

fun createDataStore(): DataStore<Preferences> {
    return createDataStore {
        val directory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        requireNotNull(directory).path + "/$DATA_STORE_FILE_NAME"
    }
}