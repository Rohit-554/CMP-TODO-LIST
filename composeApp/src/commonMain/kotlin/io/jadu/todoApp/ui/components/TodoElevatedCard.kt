package io.jadu.todoApp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import io.jadu.todoApp.ui.theme.Spacing
import io.jadu.todoApp.ui.theme.TodoColors
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Any composable passed to TodoElevatedCard will be placed inside a Box
 * The composable will have access to BoxScope functions like align(), which allows positioning within the Box
 * The parent container for the content is indeed the Box defined in lines 26-41
 * */
@Composable
@Preview(showBackground = true)
fun TodoElevatedCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .bounceClickable(onClick = onClick)
            .padding(vertical = Spacing.s2)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(Spacing.s4),
                ambientColor = TodoColors.Black.color.copy(alpha = 0.05f),
                spotColor = TodoColors.Black.color.copy(alpha = 0.12f)
            )
            .clip(RoundedCornerShape(Spacing.s4))
            .background(TodoColors.White.color)
            .padding(Spacing.s4)
    ) {
        content()
    }
}