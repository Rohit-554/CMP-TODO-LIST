package io.jadu.todoApp.ui.screens.homescreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import io.jadu.todoApp.data.model.InProgressTask
import io.jadu.todoApp.data.model.TaskCategory
import io.jadu.todoApp.ui.theme.BodyLarge
import io.jadu.todoApp.ui.theme.BodySmall
import io.jadu.todoApp.ui.theme.Spacing
import io.jadu.todoApp.ui.theme.TodoColors
import io.jadu.todoApp.ui.uiutils.VSpacer
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun InProgressTaskCard(
    task: InProgressTask = InProgressTask(
        id = "1",
        title = "Working on Jetpack Compose",
        category = TaskCategory.Office,
        progressPercentage = 0.5f,
    )
) {
    Box(
        modifier = Modifier
            .height(Spacing.s30)
            .width(Spacing.s55)
            .background(
                color = task.category.backgroundColor,
                shape = RoundedCornerShape(Spacing.s4)
            )
            .padding(Spacing.s4)
    ){
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = task.category.displayName,
                style = BodySmall().copy(
                    color = TodoColors.Secondary.color
                )
            )
            VSpacer(Spacing.s2)
            //Task Title
            Text(
                text = task.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = BodyLarge().copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            )
            VSpacer(Spacing.s3)
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = { task.progressPercentage },
                trackColor = TodoColors.GreyBackground.color,
                color = task.category.progressColor
            )
        }
    }
}












