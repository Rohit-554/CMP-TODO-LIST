package io.jadu.todoApp.ui.screens.homescreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.jadu.todoApp.ui.notification.NotificationViewModel
import io.jadu.todoApp.ui.notification.rememberActivity
import io.jadu.todoApp.ui.theme.BodyLarge
import io.jadu.todoApp.ui.theme.Spacing
import io.jadu.todoApp.ui.theme.TodoColors
import io.jadu.todoApp.ui.uiutils.HSpacer
import io.jadu.todoApp.ui.uiutils.VSpacer
import io.jadu.todoApp.ui.viewModel.HomeScreenViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import todo_list.composeapp.generated.resources.Res
import todo_list.composeapp.generated.resources.in_progress2
import todo_list.composeapp.generated.resources.no_task_groups
import todo_list.composeapp.generated.resources.search_no_results
import todo_list.composeapp.generated.resources.search_results
import todo_list.composeapp.generated.resources.task_group

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageContent(
    onNavigateToTaskScreen: () -> Unit,
    onNavigateToEditTask: (Long) -> Unit,
    viewModel: HomeScreenViewModel = koinInject(),
    notificationVM: NotificationViewModel = koinInject()
) {
    val uiState by viewModel.uiState.collectAsState()
    val activity = rememberActivity()
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = uiState.isRefreshing,
        onRefresh = viewModel::refresh,
        state = pullToRefreshState,
        modifier = Modifier.fillMaxSize(),
        indicator = {
            PullToRefreshDefaults.Indicator(
                state = pullToRefreshState,
                isRefreshing = uiState.isRefreshing,
                containerColor = TodoColors.LightPrimary.color,
                color = TodoColors.Primary.color,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.padding(Spacing.s4)
        ) {
            UserHeader(
                userProfile = uiState.userProfile,
                onNotificationClick = {
                    notificationVM.onEnableNotificationsClicked(activity)
                }
            )
            VSpacer(Spacing.s4)
            SearchBar(
                query = uiState.searchQuery,
                onQueryChange = viewModel::onSearchQueryChange,
                onClear = viewModel::clearSearch
            )
        }

        // Search results view
        AnimatedVisibility(visible = uiState.isSearchActive) {
            Column(modifier = Modifier.padding(Spacing.s4)) {
                SectionHeader(
                    title = stringResource(Res.string.search_results),
                    count = uiState.searchResults.size
                )
                if (uiState.searchResults.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = Spacing.s12),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(Res.string.search_no_results, uiState.searchQuery),
                            style = BodyLarge().copy(
                                color = TodoColors.Secondary.color,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                } else {
                    uiState.searchResults.forEach { todo ->
                        VSpacer(Spacing.s2)
                        SearchResultItem(
                            todo = todo,
                            searchQuery = uiState.searchQuery,
                            onClick = { onNavigateToEditTask(todo.id) }
                        )
                    }
                }
            }
        }

        // Normal content (hidden during search)
        AnimatedVisibility(visible = !uiState.isSearchActive) {
            Column {
                Column(modifier = Modifier.padding(Spacing.s4)) {
                    TaskProgressCard(
                        progress = uiState.todayProgress,
                        onViewTaskClick = onNavigateToTaskScreen
                    )
                    if (uiState.inProgressTasks.isNotEmpty()) {
                        VSpacer(Spacing.s4)
                        SectionHeader(
                            title = stringResource(Res.string.in_progress2),
                            count = uiState.inProgressTasks.size
                        )
                    }
                }

                AnimatedVisibility(visible = uiState.inProgressTasks.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        itemsIndexed(
                            items = uiState.inProgressTasks,
                            key = { _, task -> task.id }
                        ) { idx, task ->
                            if (idx == 0) HSpacer(Spacing.s4)
                            InProgressTaskCard(task)
                            if (idx == uiState.inProgressTasks.lastIndex) {
                                HSpacer(Spacing.s4)
                            }
                        }
                    }
                }

                Column(modifier = Modifier.padding(Spacing.s4)) {
                    SectionHeader(
                        title = stringResource(Res.string.task_group),
                        count = uiState.taskGroups.size
                    )
                    if (uiState.taskGroups.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = Spacing.s12),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(Res.string.no_task_groups),
                                style = BodyLarge().copy(
                                    color = TodoColors.Secondary.color,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                    } else {
                        uiState.taskGroups.forEach { taskGroup ->
                            TaskGroupCard(taskGroup)
                        }
                    }
                }
            }
        }

        VSpacer(Spacing.s55)
    }
    } // end PullToRefreshBox
}


