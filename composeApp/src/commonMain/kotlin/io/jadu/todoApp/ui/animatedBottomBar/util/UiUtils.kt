package io.jadu.todoApp.ui.animatedBottomBar.util

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.jadu.todoApp.ui.animatedBottomBar.models.IconSource
import org.jetbrains.compose.resources.painterResource

@Composable
fun RenderIcon(
    iconSource: IconSource,
    contentDescription: String? = null,
    tint: Color,
    modifier: Modifier
) {
    when (iconSource) {
        is IconSource.Vector -> {
            Icon(
                imageVector = iconSource.imageVector,
                contentDescription = contentDescription,
                tint = tint,
                modifier = modifier
            )
        }
        is IconSource.Drawable -> {
            Icon(
                painter = painterResource(iconSource.resId),
                contentDescription = contentDescription,
                tint = tint,
                modifier = modifier
            )
        }
    }
}