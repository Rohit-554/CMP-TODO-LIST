package io.jadu.todoApp.ui.uiutils

import android.content.res.Resources
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

actual val screenDensity: Float
    get() = Resources.getSystem().displayMetrics.density

actual val screenWidth: Dp
    get() {
        val pixels = Resources.getSystem().displayMetrics.widthPixels.toFloat()
        return (pixels / screenDensity).dp
    }

actual val screenHeight: Dp
    get() {
        val pixels = Resources.getSystem().displayMetrics.heightPixels.toFloat()
        return (pixels / screenDensity).dp
    }