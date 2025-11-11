package io.jadu.todoApp.ui.uiutils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import io.jadu.todoApp.ui.theme.Spacing

@Composable
fun HSpacer(width: Dp = Spacing.s4) {
    Spacer(modifier = Modifier.width(width))
}

@Composable
fun VSpacer(factor: Int) {
    Spacer(modifier = Modifier.height(Spacing.s4 * factor))
}

@Composable
fun VSpacer(height: Dp) {
    Spacer(modifier = Modifier.height(height))
}