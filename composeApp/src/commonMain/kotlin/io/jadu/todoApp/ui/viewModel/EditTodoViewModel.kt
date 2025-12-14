package io.jadu.todoApp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.jadu.todoApp.data.local.TodoDao
import io.jadu.todoApp.data.model.TaskCategory
import io.jadu.todoApp.data.model.TaskGroupCategory
import io.jadu.todoApp.data.model.TaskPriority
import io.jadu.todoApp.data.model.TaskStatus
import io.jadu.todoApp.ui.utils.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class EditTodoUiState(
    val todoId: Long = 0,
    val title: String = "",
    val description: String = "",
    val selectedGroupCategory: TaskGroupCategory = TaskGroupCategory.DailyStudy,
    val selectedStatus: TaskStatus = TaskStatus.TO_DO,
    val startDate: String? = null,
    val endDate: String? = null,
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val isDeleted: Boolean = false
)

class EditTodoViewModel(
    private val todoDao: TodoDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditTodoUiState())
    val uiState: StateFlow<EditTodoUiState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<UiEvent>()
    // Expose as a Flow
    val uiEvents = _uiEvents.receiveAsFlow()

    fun loadTodo(todoId: Long) {
        viewModelScope.launch {
            try {
                val todo = todoDao.getById(todoId)
                if (todo != null) {
                    _uiState.update {
                        it.copy(
                            todoId = todo.id,
                            title = todo.title,
                            description = todo.description,
                            selectedGroupCategory = todo.groupCategory,
                            selectedStatus = todo.status,
                            startDate = todo.scheduledDate,
                            endDate = null,
                            priority = todo.priority
                        )
                    }
                } else {
                    _uiEvents.send(UiEvent.ShowError("Todo not found"))
                }
            } catch (e: Exception) {
                _uiEvents.send(UiEvent.OnSuccess("Failed to load todo: ${e.message}"))
            }
        }
    }

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun updateGroupCategory(category: TaskGroupCategory) {
        _uiState.update { it.copy(selectedGroupCategory = category) }
    }

    fun updateStatus(status: TaskStatus) {
        _uiState.update { it.copy(selectedStatus = status) }
    }

    fun updateStartDate(date: String) {
        _uiState.update { it.copy(startDate = date) }
    }

    fun updateEndDate(date: String) {
        _uiState.update { it.copy(endDate = date) }
    }

    fun updatePriority(priority: TaskPriority) {
        _uiState.update { it.copy(priority = priority) }
    }

    private fun mapGroupCategoryToTaskCategory(groupCategory: TaskGroupCategory): TaskCategory {
        return when (groupCategory) {
            TaskGroupCategory.OfficeProject -> TaskCategory.Office
            TaskGroupCategory.PersonalProject -> TaskCategory.Personal
            TaskGroupCategory.DailyStudy -> TaskCategory.Study
            TaskGroupCategory.Other -> TaskCategory.Other
        }
    }

    @OptIn(ExperimentalTime::class)
    fun saveTodo() {
        val state = _uiState.value

        if (state.title.isBlank()) {
            viewModelScope.launch {
                _uiEvents.send(UiEvent.OnSuccess("Title cannot be empty"))
            }
            return
        }

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                val existingTodo = todoDao.getById(state.todoId)
                if (existingTodo != null) {
                    val updatedTodo = existingTodo.copy(
                        title = state.title,
                        description = state.description,
                        category = mapGroupCategoryToTaskCategory(state.selectedGroupCategory),
                        groupCategory = state.selectedGroupCategory,
                        status = state.selectedStatus,
                        scheduledDate = state.startDate,
                        priority = state.priority,
                        updatedAt = Clock.System.now(),
                        completed = state.selectedStatus == TaskStatus.DONE,
                        completedAt = if (state.selectedStatus == TaskStatus.DONE) Clock.System.now() else null
                    )
                    todoDao.update(updatedTodo)
                    _uiState.update { it.copy(isLoading = false, isSaved = true) }
                } else {
                    _uiEvents.send(UiEvent.ShowError("Todo Not found!"))
                    _uiState.update { it.copy(isLoading = false) }
                }
            } catch (e: Exception) {
                _uiEvents.send(UiEvent.ShowError("Failed to update todo:  ${e.message}"))
            }
        }
    }

    fun deleteTodo() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                todoDao.deleteById(_uiState.value.todoId)
                _uiState.update { it.copy(isLoading = false, isDeleted = true) }
            } catch (e: Exception) {
                _uiEvents.send(UiEvent.ShowError("Failed to delete todo: ${e.message}"))

                _uiState.update {
                    it.copy(
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun resetState() {
        _uiState.update { EditTodoUiState() }
    }
}

