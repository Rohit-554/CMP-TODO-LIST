package io.jadu.todoApp.ui.screens.homescreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.jadu.todoApp.data.model.InProgressTask
import io.jadu.todoApp.data.model.TaskCategory
import io.jadu.todoApp.data.model.TaskGroup
import io.jadu.todoApp.data.model.TaskGroupCategory
import io.jadu.todoApp.ui.theme.Spacing
import io.jadu.todoApp.ui.uiutils.HSpacer
import io.jadu.todoApp.ui.uiutils.VSpacer

@Composable
fun HomePageContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.padding(Spacing.s4)
        ) {
            UserHeader()
            VSpacer(Spacing.s6)
            TaskProgressCard()
            VSpacer(Spacing.s4)
            SectionHeader(title = "In Progress", count = 4)
        }

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(
                items = inProgressTasks,
                key = { _, task -> task.id }
            ) { idx, task ->
                if(idx == 0) HSpacer(Spacing.s4)
                InProgressTaskCard(task)
                if (idx == inProgressTasks.lastIndex) {
                    HSpacer(Spacing.s4)
                }
            }
        }

        Column(
            modifier = Modifier.padding(Spacing.s4)
        ) {
            SectionHeader(title = "Task Group", count = taskGroups.size)
            taskGroups.forEach {
                TaskGroupCard(it)
            }
        }

        VSpacer(Spacing.s55)
    }
}

val taskGroups = listOf(
    TaskGroup("1", TaskGroupCategory.OfficeProject, 23, 70),
    TaskGroup("2", TaskGroupCategory.PersonalProject, 30, 52),
    TaskGroup("3", TaskGroupCategory.DailyStudy, 30, 87)
)

val inProgressTasks = listOf(
    InProgressTask("1", "Design meeting slides", TaskCategory.Office, 0.7f),
    InProgressTask("2", "Grocery shopping", TaskCategory.Personal, 0.4f),
    InProgressTask("3", "Revise Jetpack Compose notes", TaskCategory.Study, 0.9f),
    InProgressTask("4", "Miscellaneous chores", TaskCategory.Other, 0.3f)
)

