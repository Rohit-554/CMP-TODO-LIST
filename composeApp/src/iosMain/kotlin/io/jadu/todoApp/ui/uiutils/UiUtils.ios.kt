package io.jadu.todoApp.ui.uiutils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.UIScreen

actual val screenDensity: Float
    get() = UIScreen.mainScreen.scale.toFloat()

@OptIn(ExperimentalForeignApi::class)
actual val screenWidth: Dp
    get() = UIScreen.mainScreen.bounds.useContents { size.width }.dp

@OptIn(ExperimentalForeignApi::class)
actual val screenHeight: Dp
    get() = UIScreen.mainScreen.bounds.useContents { size.height }.dp