package io.jadu.todoApp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.jadu.todoApp.data.local.TodoDao
import io.jadu.todoApp.data.model.TaskCategory
import io.jadu.todoApp.data.model.TaskGroupCategory
import io.jadu.todoApp.data.model.TaskPriority
import io.jadu.todoApp.data.model.TaskStatus
import io.jadu.todoApp.data.model.TodoItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
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
)

sealed class UiEvent {
    data class ShowError(val message: String) : UiEvent()
    data class OnSuccess(val message: String) : UiEvent()
}

class AddProjectViewModel(
    private val todoDao: TodoDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddProjectUiState())
    val uiState: StateFlow<AddProjectUiState> = _uiState.asStateFlow()


    private val _uiEvents = Channel<UiEvent>()
    // Expose as a Flow
    val uiEvents = _uiEvents.receiveAsFlow()

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
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

        //validate State
        when {
            state.title.isBlank() -> {
                viewModelScope.launch {
                    _uiEvents.send(UiEvent.ShowError("Title cannot be empty"))
                    return@launch
                }
                return
            }
            state.startDate.isNullOrEmpty() -> {
                viewModelScope.launch {
                    _uiEvents.send(UiEvent.ShowError("Start Date cannot be empty"))
                    return@launch
                }
                return
            }
        }

        _uiState.update { it.copy(isLoading = true) }

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
                _uiState.update { it.copy(isLoading = false) }
                _uiEvents.send(UiEvent.OnSuccess("Project saved Successfully"))
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                    )
                }
                _uiEvents.send(UiEvent.ShowError("Failed to save project: ${e.message}"))
                return@launch
            }
        }
    }

    fun resetState() {
        _uiState.value = AddProjectUiState()
    }
}
