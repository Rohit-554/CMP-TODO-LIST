package io.jadu.todoApp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import io.jadu.todoApp.data.model.TaskStatus
import io.jadu.todoApp.ui.theme.BodyLarge
import io.jadu.todoApp.ui.theme.BodyXSmall
import io.jadu.todoApp.ui.theme.Spacing
import io.jadu.todoApp.ui.theme.TodoColors
import io.jadu.todoApp.ui.uiutils.VSpacer
import org.jetbrains.compose.ui.tooling.preview.Preview

data class TaskStatusOption(
    val status: TaskStatus,
    val displayName: String,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun SelectTaskStatusBottomSheet(
    selectedStatus: TaskStatus = TaskStatus.TO_DO,
    onDismiss: () -> Unit = {},
    onStatusSelected: (TaskStatus) -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val selectedIndex = remember { mutableStateOf(selectedStatus) }

    val statusOptions = listOf(
        TaskStatusOption(TaskStatus.TO_DO, "To Do", TodoColors.Primary.color),
        TaskStatusOption(TaskStatus.IN_PROGRESS, "In Progress", TodoColors.Orange.color),
        TaskStatusOption(TaskStatus.DONE, "Done", TodoColors.Emerald.color)
    )

    ModalBottomSheet(
        containerColor = TodoColors.Light.color,
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        dragHandle = null
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = Spacing.s4)
        ) {
            item {
                StatusBottomSheetHeader(
                    onCancelClick = onDismiss
                )
                VSpacer(Spacing.s4)
            }
            itemsIndexed(
                items = statusOptions
            ) { _, statusOption ->
                StatusBottomSheetItem(
                    statusOption = statusOption,
                    isSelected = statusOption.status == selectedIndex.value,
                    onClick = {
                        selectedIndex.value = statusOption.status
                        onStatusSelected(statusOption.status)
                    }
                )
            }
        }
    }
}

@Composable
fun StatusBottomSheetHeader(
    onCancelClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(top = Spacing.s6)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Select Task Status",
                style = BodyLarge().copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Icon(
                modifier = Modifier.bounceClickable(onClick = onCancelClick),
                imageVector = Icons.Outlined.Close,
                contentDescription = ""
            )
        }
    }
}

@Composable
fun StatusBottomSheetItem(
    isSelected: Boolean,
    statusOption: TaskStatusOption,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isSelected)
                    TodoColors.LightestBlue.color else TodoColors.Light.color,
                shape = RoundedCornerShape(Spacing.s4)
            )
            .padding(vertical = Spacing.s4)
            .bounceClickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.padding(horizontal = Spacing.s3),
            horizontalArrangement = Arrangement.spacedBy(Spacing.s2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(Spacing.s8)
                    .background(
                        color = statusOption.color.copy(alpha = 0.15f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(Spacing.s4)
                        .background(
                            color = statusOption.color,
                            shape = CircleShape
                        )
                )
            }
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Task Status",
                    style = BodyXSmall().copy(color = TodoColors.Secondary.color)
                )
                Text(
                    modifier = Modifier.align(Alignment.Start).padding(end = Spacing.s20),
                    text = statusOption.displayName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = BodyLarge().copy(fontWeight = FontWeight.Bold, color = Color.Black)
                )
            }
        }

        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(Spacing.s8)
                    .offset(x = -Spacing.s4)
                    .background(
                        color = TodoColors.LightBlue.color,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = TodoColors.Primary.color,
                    modifier = Modifier.size(Spacing.s4)
                )
            }
        }
    }
}

