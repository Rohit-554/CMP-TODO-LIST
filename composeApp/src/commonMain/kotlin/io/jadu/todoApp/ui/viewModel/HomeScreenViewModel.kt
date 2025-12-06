package io.jadu.todoApp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.jadu.todoApp.data.local.TodoDao
import io.jadu.todoApp.data.local.UserProfileDao
import io.jadu.todoApp.data.model.InProgressTask
import io.jadu.todoApp.data.model.TaskGroup
import io.jadu.todoApp.data.model.TaskGroupCategory
import io.jadu.todoApp.data.model.TaskStatus
import io.jadu.todoApp.data.model.TodoItem
import io.jadu.todoApp.data.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class HomeScreenUiState(
    val allTodos: List<TodoItem> = emptyList(),
    val inProgressTasks: List<InProgressTask> = emptyList(),
    val taskGroups: List<TaskGroup> = emptyList(),
    val todayProgress: Float = 0f,
    val todayTasksCount: Int = 0,
    val userProfile: UserProfile = UserProfile(),
    val isLoading: Boolean = false
)

class HomeScreenViewModel(
    private val todoDao: TodoDao,
    private val userProfileDao: UserProfileDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    init {
        loadAllData()
    }

    private fun loadAllData() {
        viewModelScope.launch {
            combine(
                todoDao.getAllAsFlow(),
                userProfileDao.getUserProfile()
            ) { todos, userProfile ->
                _uiState.update { state ->
                    state.copy(
                        allTodos = todos,
                        inProgressTasks = convertToInProgressTasks(todos),
                        taskGroups = generateTaskGroups(todos),
                        todayProgress = calculateTodayProgress(todos),
                        todayTasksCount = getTodayTasksCount(todos),
                        userProfile = userProfile ?: UserProfile()
                    )
                }
            }.collect { }
        }
    }

    private fun convertToInProgressTasks(todos: List<TodoItem>): List<InProgressTask> {
        return todos
            .filter { it.status == TaskStatus.IN_PROGRESS }
            .take(5) // Show only top 5 in-progress tasks
            .map { todo ->
                InProgressTask(
                    id = todo.id.toString(),
                    title = todo.title,
                    category = todo.category,
                    progressPercentage = todo.progressPercentage
                )
            }
    }

    private fun generateTaskGroups(todos: List<TodoItem>): List<TaskGroup> {
        val groupedByCategory = todos.groupBy { it.groupCategory }

        return TaskGroupCategory.entries.mapNotNull { category ->
            val categoryTodos = groupedByCategory[category] ?: emptyList()
            if (categoryTodos.isEmpty()) return@mapNotNull null

            val totalTasks = categoryTodos.size
            val completedTasks = categoryTodos.count { it.status == TaskStatus.DONE }
            val completionPercentage = if (totalTasks > 0) {
                (completedTasks * 100) / totalTasks
            } else 0

            TaskGroup(
                id = category.name,
                category = category,
                taskCount = totalTasks,
                completionPercentage = completionPercentage
            )
        }
    }

    private fun calculateTodayProgress(todos: List<TodoItem>): Float {
        val todayTodos = getTodayTodos(todos)
        if (todayTodos.isEmpty()) return 0f

        val completedCount = todayTodos.count { it.status == TaskStatus.DONE }
        return completedCount.toFloat() / todayTodos.size.toFloat()
    }

    private fun getTodayTasksCount(todos: List<TodoItem>): Int {
        return getTodayTodos(todos).size
    }

    @OptIn(ExperimentalTime::class)
    private fun getTodayTodos(todos: List<TodoItem>): List<TodoItem> {
        val today = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
        return todos.filter { todo ->
            todo.scheduledDate?.let { dateString ->
                parseDateString(dateString) == today
            } ?: false
        }
    }

    private fun parseDateString(dateString: String): LocalDate? {
        return try {
            // Parse "17 Nov, 2025" format
            val parts = dateString.split(" ")
            if (parts.size != 3) return null

            val day = parts[0].toIntOrNull() ?: return null
            val monthStr = parts[1].trimEnd(',')
            val year = parts[2].toIntOrNull() ?: return null

            val month = when (monthStr) {
                "Jan" -> 1
                "Feb" -> 2
                "Mar" -> 3
                "Apr" -> 4
                "May" -> 5
                "Jun" -> 6
                "Jul" -> 7
                "Aug" -> 8
                "Sep" -> 9
                "Oct" -> 10
                "Nov" -> 11
                "Dec" -> 12
                else -> return null
            }

            LocalDate(year, month, day)
        } catch (_: Exception) {
            null
        }
    }
}

