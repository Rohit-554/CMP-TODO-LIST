package io.jadu.todoApp.ui.animatedBottomBar.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import io.jadu.todoApp.ui.animatedBottomBar.util.toPx

@Composable
fun Dp.toPx(): Float = with(LocalDensity.current) { this@toPx.toPx() }