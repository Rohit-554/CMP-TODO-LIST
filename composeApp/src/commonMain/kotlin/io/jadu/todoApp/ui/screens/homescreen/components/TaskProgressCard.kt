package io.jadu.todoApp.ui.screens.homescreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import io.jadu.todoApp.ui.components.CurvedButton
import io.jadu.todoApp.ui.components.CurvedButtonConfig
import io.jadu.todoApp.ui.theme.BodyLarge
import io.jadu.todoApp.ui.theme.BodyXLarge
import io.jadu.todoApp.ui.theme.Spacing
import io.jadu.todoApp.ui.theme.TodoColors
import io.jadu.todoApp.ui.uiutils.HSpacer
import io.jadu.todoApp.ui.uiutils.VSpacer
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview(showBackground = true)
fun TaskProgressCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = TodoColors.Primary.color,
                shape = RoundedCornerShape(Spacing.s6)
            )
            .padding(Spacing.s6)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column (
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Your Today's task \n almost done",
                    style = BodyLarge().copy(
                        color = TodoColors.Light.color
                    )
                )
                VSpacer(Spacing.s6)
                CurvedButton(
                    modifier = Modifier,
                    onClick = {},
                    text = "View Task",
                    buttonConfig = CurvedButtonConfig(
                        cornerRadius = 40f,
                        verticalBulgeFactor = 10f,
                        containerColor = TodoColors.Light.color,
                        contentColor = TodoColors.Dark.color,
                        gradientShadowColor = Color.Transparent
                    )
                )
            }
            HSpacer(Spacing.s6)

            //progress bar
            Box(
                modifier = Modifier.size(Spacing.s25),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { 0.7f },
                    modifier = Modifier.size(Spacing.s25),
                    strokeWidth = Spacing.s2,
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.2f)
                )
                Text(
                    text = "70%",
                    style = BodyXLarge().copy(
                        fontWeight = FontWeight.Bold,
                        color = TodoColors.Light.color
                    )
                )
            }
        }
    }
}


















