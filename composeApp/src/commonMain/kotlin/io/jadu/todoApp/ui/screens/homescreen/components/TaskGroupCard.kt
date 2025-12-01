package io.jadu.todoApp.ui.screens.homescreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.jadu.todoApp.data.model.TaskGroup
import io.jadu.todoApp.data.model.TaskGroupCategory
import io.jadu.todoApp.ui.components.TodoElevatedCard
import io.jadu.todoApp.ui.theme.BodyLarge
import io.jadu.todoApp.ui.theme.BodySmall
import io.jadu.todoApp.ui.theme.Spacing
import io.jadu.todoApp.ui.theme.TodoColors
import io.jadu.todoApp.ui.uiutils.HSpacer
import io.jadu.todoApp.ui.uiutils.VSpacer
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview(showBackground = true)
fun TaskGroupCard(
    taskGroup: TaskGroup = TaskGroup(
        "1",
        TaskGroupCategory.OfficeProject,
        23,
        70),
) {
    val category = taskGroup.category
    TodoElevatedCard {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(Spacing.s8)
                        .background(
                            color = category.color.copy(alpha = 0.15f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = category.icon,
                        contentDescription = category.displayName,
                        tint = category.color,
                        modifier = Modifier.size(Spacing.s4)
                    )
                }
                HSpacer(Spacing.s4)
                Column {
                    Text(
                        text = category.displayName,
                        style = BodyLarge().copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    VSpacer(Spacing.s1)
                    Text(
                        text = taskGroup.taskCount.toString() + " Tasks",
                        style = BodySmall().copy(
                            color = TodoColors.Secondary.color
                        )
                    )
                }
            }

            Box(
                modifier = Modifier.size(Spacing.s13),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { taskGroup.completionPercentage / 100f },
                    modifier = Modifier.size(Spacing.s18),
                    strokeWidth = 6.dp,
                    color = category.color,
                    trackColor = category.color.copy(alpha = 0.2f)
                )
                Text(
                    text = "${taskGroup.completionPercentage}%",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TodoColors.Dark.color
                    )
                )
            }
        }
    }
}













