package io.jadu.todoApp.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.jadu.todoApp.data.model.TaskCategory
import io.jadu.todoApp.data.model.TaskGroupCategory
import io.jadu.todoApp.ui.theme.BodyLarge
import io.jadu.todoApp.ui.theme.BodyXSmall
import io.jadu.todoApp.ui.theme.Spacing
import io.jadu.todoApp.ui.theme.TodoColors
import io.jadu.todoApp.ui.uiutils.VSpacer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import todo_list.composeapp.generated.resources.Res
import todo_list.composeapp.generated.resources.arrow___down_4

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun SelectGroupBottomSheet(
    onDismiss: () -> Unit = {}
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    // selection state: store selected index (or -1 for none)
    val selectedIndex = remember { mutableStateOf(TaskGroupCategory.DailyStudy) }


    val items = listOf<TaskGroupCategory>(
        TaskGroupCategory.DailyStudy,
        TaskGroupCategory.OfficeProject
    )

    ModalBottomSheet(
        containerColor = TodoColors.Light.color,
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        dragHandle = null
    ) {
        LazyColumn (
            modifier = Modifier.padding(horizontal = Spacing.s4)
        ) {
            item {
                BottomSheetHeader(
                    onCancelClick = onDismiss
                )
                VSpacer(Spacing.s4)
            }
            itemsIndexed(
                items = items
            ) { index, category ->
                BottomSheetItem(
                    category = category,
                    isSelected = category == selectedIndex.value,
                    onClick = {
                        selectedIndex.value = category
                        onDismiss()
                    }
                )
            }
        }
    }
}

@Composable
fun BottomSheetHeader(
    onCancelClick : () -> Unit
) {
    Column(
        modifier = Modifier.padding(top = Spacing.s6)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Select Task Group",
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
fun BottomSheetItem(
    isSelected: Boolean,
    category: TaskGroupCategory = TaskGroupCategory.OfficeProject,
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
            .bounceClickable(onClick = onClick), // make entire row clickable
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
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Task Group",
                    style = BodyXSmall().copy(color = TodoColors.Secondary.color)
                )
                Text(
                    modifier = Modifier.align(Alignment.Start).padding(end = Spacing.s20),
                    text = category.displayName,
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
                )
            }
        }
    }
}

