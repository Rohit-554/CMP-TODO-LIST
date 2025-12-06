package io.jadu.todoApp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.jadu.todoApp.data.model.ScheduleTaskModel
import io.jadu.todoApp.data.model.TaskStatus
import io.jadu.todoApp.ui.theme.BodyLarge
import io.jadu.todoApp.ui.theme.BodyNormal
import io.jadu.todoApp.ui.theme.BodySmall
import io.jadu.todoApp.ui.theme.BodyXSmall
import io.jadu.todoApp.ui.theme.Spacing
import io.jadu.todoApp.ui.theme.TodoColors
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import todo_list.composeapp.generated.resources.Res
import todo_list.composeapp.generated.resources.clock_loader_icon

@Composable
@Preview
fun ScheduleTaskCard(
    task: ScheduleTaskModel = ScheduleTaskModel(),
    onClick: () -> Unit = {}
) {

    val category = task.category

    val statusText = when (task.status) {
        TaskStatus.TO_DO -> "To Do"
        TaskStatus.IN_PROGRESS -> "In Progress"
        TaskStatus.DONE -> "Done"
    }

    val statusColor = when (task.status) {
        TaskStatus.TO_DO -> TodoColors.Primary.color
        TaskStatus.IN_PROGRESS -> TodoColors.Orange.color
        TaskStatus.DONE -> TodoColors.Emerald.color
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.s2)
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(Spacing.s4),
                ambientColor = Color.Black.copy(alpha = 0.05f),
                spotColor = Color.Black.copy(alpha = 0.12f)
            )
            .clip(RoundedCornerShape(Spacing.s4))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(Spacing.s4)
    ) {

        Box(
            modifier = Modifier
                .size(Spacing.s8)
                .align(Alignment.TopEnd)
                .background(
                    color = category.color.copy(alpha = 0.12f),
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = category.icon,
                contentDescription = category.displayName,
                tint = category.color,
                modifier = Modifier.size(Spacing.s5)
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Spacing.s2)
        ) {
            // ---------- TOP ROW (Title + Icon) ----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = task.title,
                    style = BodySmall().copy(
                        color = TodoColors.Secondary.color
                    )
                )
            }

            // ---------- DESCRIPTION ----------
            Text(
                modifier = Modifier.align (Alignment.Start).padding(end = Spacing.s20),
                text = task.description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = BodyLarge().copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )


            // ---------- BOTTOM ROW (Time + Done Chip) ----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                TimeComp(task.scheduledTime)
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .background(
                    color = statusColor.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(
                        topStart = Spacing.s2,
                        bottomEnd = Spacing.s2
                    )
                ).padding(horizontal = Spacing.s2, vertical = Spacing.s1),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = statusText,
                style = BodyXSmall().copy(
                    color = statusColor,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

@Composable
fun TimeComp(time : String) {
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(Spacing.s1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(Spacing.s5),
            painter = painterResource(Res.drawable.clock_loader_icon),
            contentDescription = "",
            colorFilter = ColorFilter.tint(TodoColors.Purple.color)
        )

        Text(
            time,
            style = BodyNormal().copy(
                color = TodoColors.Purple.color
            )
        )

    }
}