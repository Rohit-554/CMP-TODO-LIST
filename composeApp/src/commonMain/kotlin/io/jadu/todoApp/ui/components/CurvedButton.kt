package io.jadu.todoApp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.jadu.todoApp.ui.theme.BodyXLarge
import io.jadu.todoApp.ui.theme.TodoColors

@Composable
fun CurvedButton(
    modifier: Modifier = Modifier,
    buttonConfig : CurvedButtonConfig = CurvedButtonConfig(),
    text: String = "",
    isEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .wrapContentWidth()
            .height(56.dp)
            .bounceClickable(
                onClick = onClick,
                enabled = isEnabled
            ),
        contentAlignment = Alignment.Center
    ) {
        if (isEnabled) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = 6.dp)
                    .blur(radius = 12.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
            ) {
                val width = size.width
                val height = size.height

                val path = createBulgedPath(
                    width = width,
                    height = height,
                    cornerRadius = buttonConfig.cornerRadius,
                    bulgeFactor = buttonConfig.verticalBulgeFactor
                )

                drawPath(
                    path = path,
                    color = buttonConfig.gradientShadowColor,
                    style = Fill
                )
            }
        }

        // Custom button background with bulge
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val width = size.width
            val height = size.height

            val path = createBulgedPath(
                width = width,
                height = height,
                cornerRadius = buttonConfig.cornerRadius,
                bulgeFactor = buttonConfig.verticalBulgeFactor
            )


            // Draw main button
            translate(top = -2f) {
                drawPath(
                    path = path,
                    color = if(isEnabled) buttonConfig.containerColor
                    else TodoColors.BorderGrey.color.copy(alpha = 0.5f),
                    style = Fill
                )
            }
        }

        // Button content
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = BodyXLarge().copy(
                    fontSize = buttonConfig.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = if (isEnabled) buttonConfig.contentColor
                    else TodoColors.Dark.color
                ),
            )
            if (buttonConfig.shouldShowArrow)  {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "→",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    color = buttonConfig.contentColor
                )
            }
        }
    }
}

fun createBulgedPath(
    width: Float,
    height: Float,
    cornerRadius: Float,
    bulgeFactor: Float
): Path {
    return Path().apply {
        // Start from top-left corner
        moveTo(cornerRadius, 0f)

        // Top edge with slight bulge
        quadraticTo(
            width * 0.5f, -bulgeFactor, width - cornerRadius, 0f
        )

        // Top-right corner
        quadraticTo(width, 0f, width, cornerRadius)

        // Right edge with bulge
        quadraticTo(
            width, height * 0.5f, width, height - cornerRadius
        )

        // Bottom-right corner
        quadraticTo(width, height, width - cornerRadius, height)

        // Bottom edge with slight bulge
        quadraticTo(
            width * 0.5f, height + bulgeFactor, cornerRadius, height
        )

        // Bottom-left corner
        quadraticTo(0f, height, 0f, height - cornerRadius)

        // Left edge with bulge
        quadraticTo(
            0f, height * 0.5f, 0f, cornerRadius
        )

        // Top-left corner completion
        quadraticTo(0f, 0f, cornerRadius, 0f)

        close()
    }
}

/**
 * Configuration class for styling and customizing the CurvedButton appearance.
 *
 * @param containerColor The background color of the button
 * @param contentColor The color of the button text and arrow icon
 * @param gradientShadowColor The color of the shadow effect below the button
 * @param verticalBulgeFactor The intensity of the vertical bulge effect on top and bottom edges
 */
data class CurvedButtonConfig (
    val containerColor: Color = TodoColors.Primary.color,
    val contentColor : Color = TodoColors.Light.color,
    val fontSize : TextUnit = 18.sp,
    val gradientShadowColor : Color = TodoColors.Primary.color.copy(alpha = 0.4f),
    val verticalBulgeFactor : Float = 8f,
    val cornerRadius : Float = 50f,
    val shouldShowArrow : Boolean = false
)