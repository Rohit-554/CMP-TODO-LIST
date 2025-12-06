package io.jadu.todoApp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.jadu.todoApp.data.local.TodoDao
import io.jadu.todoApp.data.model.TaskStatus
import io.jadu.todoApp.data.model.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class TaskScreenUiState(
    val selectedDate: LocalDate? = null,
    val allTodos: List<TodoItem> = emptyList(),
    val filteredTodos: List<TodoItem> = emptyList(),
    val selectedFilter: String = "All",
    val isLoading: Boolean = false
)

class TaskScreenViewModel(
    private val todoDao: TodoDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskScreenUiState())
    val uiState: StateFlow<TaskScreenUiState> = _uiState.asStateFlow()

    init {
        loadAllTodos()
        setTodayAsSelectedDate()
    }

    @OptIn(ExperimentalTime::class)
    private fun setTodayAsSelectedDate() {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        _uiState.update { it.copy(selectedDate = today) }
    }

    fun loadAllTodos() {
        viewModelScope.launch {
            todoDao.getAllAsFlow().collect { todos ->
                _uiState.update { state ->
                    state.copy(
                        allTodos = todos,
                        filteredTodos = filterTodos(todos, state.selectedDate, state.selectedFilter)
                    )
                }
            }
        }
    }

    fun onDateSelected(date: LocalDate) {
        _uiState.update { state ->
            state.copy(
                selectedDate = date,
                filteredTodos = filterTodos(state.allTodos, date, state.selectedFilter)
            )
        }
    }

    fun onFilterSelected(filter: String) {
        _uiState.update { state ->
            state.copy(
                selectedFilter = filter,
                filteredTodos = filterTodos(state.allTodos, state.selectedDate, filter)
            )
        }
    }

    private fun filterTodos(
        todos: List<TodoItem>,
        selectedDate: LocalDate?,
        filter: String
    ): List<TodoItem> {
        var filtered = todos

        // Filter by selected date
        if (selectedDate != null) {
            filtered = filtered.filter { todo ->
                todo.scheduledDate?.let { dateString ->
                    // Parse the date string (format: "dd MMM, yyyy" e.g., "17 Nov, 2025")
                    parseDateString(dateString) == selectedDate
                } ?: false
            }
        }

        // Filter by status
        filtered = when (filter) {
            "To do" -> filtered.filter { it.status == TaskStatus.TO_DO }
            "on Going" -> filtered.filter { it.status == TaskStatus.IN_PROGRESS }
            "Done" -> filtered.filter { it.status == TaskStatus.DONE }
            "All" -> filtered
            else -> filtered
        }

        return filtered
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
        } catch (e: Exception) {
            null
        }
    }

    fun toggleTodoComplete(todoId: Long) {
        viewModelScope.launch {
            val todo = todoDao.getById(todoId)
            if (todo != null) {
                val updated = todo.copy(
                    completed = !todo.completed,
                    status = if (!todo.completed) TaskStatus.DONE else TaskStatus.TO_DO
                )
                todoDao.update(updated)
            }
        }
    }
}

