package io.jadu.todoApp.ui.uiutils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import io.jadu.todoApp.ui.theme.Spacing
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Month

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


fun DayOfWeek.shortName(): String {
    return this.name.lowercase().let { lower ->
        lower.take(1).uppercase() + lower.drop(1)
    }.run {
        take(3)
    }
}

fun Month.shortName(): String {
    return this.name.lowercase().let {
        it.take(1).uppercase() + it.drop(1)
    }.run {
        take(3)
    }
}

expect val screenDensity: Float
expect val screenWidth: Dp
expect val screenHeight: Dp