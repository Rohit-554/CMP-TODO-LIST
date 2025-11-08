package io.jadu.todoApp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview(showBackground = true, )
fun CustomButton(
    text: String = "",
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 6.dp)
                .blur(radius = 12.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
        ) {
            val width = size.width
            val height = size.height
            val cornerRadius = 50f
            val bulgeFactor = 8f

            val path = Path().apply {
                // Start from top-left corner
                moveTo(cornerRadius, 0f)

                // Top edge with slight bulge
                quadraticTo(
                    width * 0.5f, -bulgeFactor,
                    width - cornerRadius, 0f
                )

                // Top-right corner
                quadraticTo(width, 0f, width, cornerRadius)

                // Right edge with bulge
                quadraticTo(
                    width, height * 0.5f,
                    width, height - cornerRadius
                )

                // Bottom-right corner
                quadraticTo(width, height, width - cornerRadius, height)

                // Bottom edge with slight bulge
                quadraticTo(
                    width * 0.5f, height + bulgeFactor,
                    cornerRadius, height
                )

                // Bottom-left corner
                quadraticTo(0f, height, 0f, height - cornerRadius)

                // Left edge
                quadraticTo(
                    0f, height * 0.5f,
                    0f, cornerRadius
                )

                // Top-left corner completion
                quadraticTo(0f, 0f, cornerRadius, 0f)

                close()
            }

            drawPath(
                path = path,
                color = Color(0xFF3D2A8F).copy(alpha = 0.4f),
                style = androidx.compose.ui.graphics.drawscope.Fill
            )
        }

        // Custom button background with bulge
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val width = size.width
            val height = size.height
            val cornerRadius = 50f
            val bulgeFactor = 8f // Controls how much the sides bulge out

            val path = Path().apply {
                moveTo(cornerRadius, 0f)
                quadraticTo(
                    width * 0.5f, -bulgeFactor,
                    width - cornerRadius, 0f
                )
                quadraticTo(width, 0f, width, cornerRadius)
                quadraticTo(
                    width, height * 0.5f,
                    width, height - cornerRadius
                )
                quadraticTo(width, height, width - cornerRadius, height)
                quadraticTo(
                    width * 0.5f, height + bulgeFactor,
                    cornerRadius, height
                )
                quadraticTo(0f, height, 0f, height - cornerRadius)
                quadraticTo(
                    0f, height * 0.5f,
                    0f, cornerRadius
                )
                quadraticTo(0f, 0f, cornerRadius, 0f)

                close()
            }


            // Draw main button
            translate(top = -2f) {
                drawPath(
                    path = path,
                    color = Color(0xFF5B3FDB),
                    style = Fill
                )
            }
        }

        // Button content
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "→",
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        }
    }
}