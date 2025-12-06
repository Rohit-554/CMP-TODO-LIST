package io.jadu.todoApp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import io.jadu.todoApp.data.model.toScheduleTaskModel
import io.jadu.todoApp.ui.components.CarouselCalendar
import io.jadu.todoApp.ui.components.CurvedButton
import io.jadu.todoApp.ui.components.CurvedButtonConfig
import io.jadu.todoApp.ui.components.ScheduleTaskCard
import io.jadu.todoApp.ui.components.TodoTopAppBar
import io.jadu.todoApp.ui.screens.onBoarding.TodoBackgroundScreen
import io.jadu.todoApp.ui.theme.BodyLarge
import io.jadu.todoApp.ui.theme.Spacing
import io.jadu.todoApp.ui.theme.TodoColors
import io.jadu.todoApp.ui.uiutils.HSpacer
import io.jadu.todoApp.ui.uiutils.VSpacer
import io.jadu.todoApp.ui.viewModel.TaskScreenViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
@Preview
fun TaskScreen(
    navController: NavController,
    viewModel: TaskScreenViewModel = koinInject()
) {
    val uiState by viewModel.uiState.collectAsState()

    val chips = listOf(
        "All",
        "To do",
        "on Going",
        "Done"
    )

    TodoBackgroundScreen {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TodoTopAppBar(
                modifier = Modifier.statusBarsPadding().systemBarsPadding(),
                navController = navController
            )

            CarouselCalendar(
                initialSelectedDate = uiState.selectedDate,
                onDateSelected = { date ->
                    viewModel.onDateSelected(date)
                }
            )
            VSpacer(Spacing.s4)

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(chips) { idx, title ->
                    val curr = chips[idx]
                    if (idx == 0) HSpacer(Spacing.s4)

                    CurvedButton(
                        modifier = Modifier.width(100.dp).height(40.dp),
                        text = title,
                        buttonConfig = CurvedButtonConfig(
                            containerColor = if (curr == uiState.selectedFilter) TodoColors.Primary.color else TodoColors.LightPrimary.color,
                            contentColor = if (curr == uiState.selectedFilter) TodoColors.Light.color else TodoColors.Primary.color,
                            fontSize = 14.sp,
                            verticalBulgeFactor = 8f,
                            cornerRadius = 28f,
                            gradientShadowColor = Color.Transparent
                        )
                    ) {
                        viewModel.onFilterSelected(title)
                    }

                    if (idx == chips.lastIndex) {
                        HSpacer(Spacing.s4)
                    }
                }
            }
            VSpacer(Spacing.s4)

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = Spacing.s4)
            ) {
                if (uiState.filteredTodos.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = Spacing.s12),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No tasks for this date",
                                style = BodyLarge().copy(
                                    color = TodoColors.Secondary.color,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                    }
                } else {
                    items(
                        items = uiState.filteredTodos,
                        key = { it.id }
                    ) { todo ->
                        ScheduleTaskCard(
                            task = todo.toScheduleTaskModel(),
                            onClick = {
                                navController.navigate(io.jadu.todoApp.ui.navigation.NavRoute.EditTodo(todoId = todo.id))
                            }
                        )
                    }
                }
            }
        }

    }
}