package io.jadu.todoApp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.jadu.todoApp.data.local.TodoDao
import io.jadu.todoApp.data.model.TaskCategory
import io.jadu.todoApp.data.model.TaskGroupCategory
import io.jadu.todoApp.data.model.TaskPriority
import io.jadu.todoApp.data.model.TaskStatus
import io.jadu.todoApp.data.model.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

data class AddProjectUiState(
    val title: String = "",
    val description: String = "",
    val selectedGroupCategory: TaskGroupCategory = TaskGroupCategory.DailyStudy,
    val startDate: String? = null,
    val endDate: String? = null,
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSaved: Boolean = false
)

class AddProjectViewModel(
    private val todoDao: TodoDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddProjectUiState())
    val uiState: StateFlow<AddProjectUiState> = _uiState.asStateFlow()

    private val _allTodos = MutableStateFlow<List<TodoItem>>(emptyList())
    val allTodos: StateFlow<List<TodoItem>> = _allTodos.asStateFlow()

    init {
        loadAllTodos()
    }

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title, errorMessage = null) }
    }

    fun updateDescription(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun updateGroupCategory(category: TaskGroupCategory) {
        _uiState.update { it.copy(selectedGroupCategory = category) }
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
    fun saveProject() {
        val state = _uiState.value

        // Validation
        if (state.title.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Title cannot be empty") }
            return
        }

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            try {
                val todoItem = TodoItem(
                    title = state.title,
                    description = state.description,
                    category = mapGroupCategoryToTaskCategory(state.selectedGroupCategory),
                    groupCategory = state.selectedGroupCategory,
                    scheduledDate = state.startDate,
                    priority = state.priority,
                    status = TaskStatus.TO_DO,
                    createdAt = Clock.System.now(),
                    updatedAt = Clock.System.now()
                )

                todoDao.insert(todoItem)
                _uiState.update { it.copy(isLoading = false, isSaved = true) }
                loadAllTodos() // Refresh the list
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to save project: ${e.message}"
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    fun updateProject(id: Long) {
        val state = _uiState.value

        if (state.title.isBlank()) {
            _uiState.update { it.copy(errorMessage = "Title cannot be empty") }
            return
        }

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            try {
                val existingTodo = todoDao.getById(id)
                if (existingTodo != null) {
                    val updatedTodo = existingTodo.copy(
                        title = state.title,
                        description = state.description,
                        category = mapGroupCategoryToTaskCategory(state.selectedGroupCategory),
                        groupCategory = state.selectedGroupCategory,
                        scheduledDate = state.startDate,
                        priority = state.priority,
                        updatedAt = Clock.System.now()
                    )
                    todoDao.update(updatedTodo)
                    _uiState.update { it.copy(isLoading = false, isSaved = true) }
                    loadAllTodos()
                } else {
                    _uiState.update { it.copy(isLoading = false, errorMessage = "Todo not found") }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to update project: ${e.message}"
                    )
                }
            }
        }
    }

    fun deleteProject(id: Long) {
        viewModelScope.launch {
            try {
                todoDao.deleteById(id)
                loadAllTodos()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "Failed to delete project: ${e.message}")
                }
            }
        }
    }

    fun loadAllTodos() {
        viewModelScope.launch {
            todoDao.getAllAsFlow().collect { todos ->
                _allTodos.value = todos
            }
        }
    }

    fun resetState() {
        _uiState.value = AddProjectUiState()
    }
}
