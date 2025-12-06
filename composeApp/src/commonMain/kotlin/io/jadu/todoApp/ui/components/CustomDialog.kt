package io.jadu.todoApp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.jadu.todoApp.ui.theme.BodyLarge
import io.jadu.todoApp.ui.theme.BodyNormal
import io.jadu.todoApp.ui.theme.Spacing
import io.jadu.todoApp.ui.theme.TodoColors
import io.jadu.todoApp.ui.uiutils.HSpacer
import io.jadu.todoApp.ui.uiutils.VSpacer
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

data class CustomDialogConfig(
    val title: String,
    val subtitle: String,
    val image: DrawableResource? = null,
    val confirmText: String = "OK",
    val cancelText: String = "Cancel",
    val showCancelButton: Boolean = true
)

@Composable
fun CustomDialog(
    config: CustomDialogConfig,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        TodoElevatedCard (
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.s4),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.s6),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Title
                Text(
                    text = config.title,
                    style = BodyLarge().copy(
                        fontWeight = FontWeight.Bold,
                        color = TodoColors.Dark.color
                    ),
                    textAlign = TextAlign.Center
                )

                VSpacer(Spacing.s3)

                // Subtitle
                Text(
                    text = config.subtitle,
                    style = BodyNormal().copy(
                        color = TodoColors.Secondary.color
                    ),
                    textAlign = TextAlign.Center
                )

                // Optional Image
                config.image?.let { imageRes ->
                    VSpacer(Spacing.s4)
                    Image(
                        painter = painterResource(imageRes),
                        contentDescription = null,
                        modifier = Modifier.size(Spacing.s40)
                    )
                }

                VSpacer(Spacing.s6)

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = Spacing.s10,),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.s3),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (config.showCancelButton) {
                        CurvedButton(
                            modifier = Modifier.weight(1f).height(Spacing.s8),
                            buttonConfig = CurvedButtonConfig(
                                containerColor = TodoColors.Light.color,
                                contentColor = TodoColors.Dark.color,
                                cornerRadius = Spacing.s3.value,
                                gradientShadowColor = Color.Transparent,
                                verticalBulgeFactor = 0f
                            ),
                            text = config.cancelText
                        ) {
                            onDismiss()
                        }
                        HSpacer(Spacing.s2)
                    }

                    CurvedButton(
                        modifier = Modifier.weight(1f).height(Spacing.s8),
                        buttonConfig = CurvedButtonConfig(
                            cornerRadius = Spacing.s3.value,
                            gradientShadowColor = Color.Transparent,
                            verticalBulgeFactor = 0f
                        ),
                        text = config.confirmText
                    ) {
                        onConfirm()
                    }
                }
            }
        }
    }
}

