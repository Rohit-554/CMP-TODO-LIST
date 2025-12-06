package io.jadu.todoApp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.jadu.todoApp.data.local.TodoDao
import io.jadu.todoApp.data.model.TaskGroupCategory
import io.jadu.todoApp.ui.components.SphereLabel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import todo_list.composeapp.generated.resources.Res
import todo_list.composeapp.generated.resources.book
import todo_list.composeapp.generated.resources.briefcase
import todo_list.composeapp.generated.resources.calendar
import todo_list.composeapp.generated.resources.close_up_of_pink_coffee_cup
import todo_list.composeapp.generated.resources.document_text
import todo_list.composeapp.generated.resources.home
import todo_list.composeapp.generated.resources.multicolored_smartphone_notifications
import todo_list.composeapp.generated.resources.user_octagon

data class MostUsedCategoryUiState(
    val labels: List<SphereLabel> = emptyList(),
    val isLoading: Boolean = false
)

class MostUsedCategoryViewModel(
    private val todoDao: TodoDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(MostUsedCategoryUiState())
    val uiState: StateFlow<MostUsedCategoryUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            todoDao.getUniqueGroupCategories().collect { categoryNames ->
                val labels = if (categoryNames.isNotEmpty()) {
                    // Convert category names to TaskGroupCategory and then to SphereLabels
                    categoryNames.mapNotNull { name ->
                        try {
                            val category = TaskGroupCategory.valueOf(name)
                            SphereLabel(
                                text = category.displayName,
                                icon = getCategoryIcon(category)
                            )
                        } catch (e: Exception) {
                            null
                        }
                    }
                } else {
                    // Use default labels if no categories exist
                    getDefaultLabels()
                }

                _uiState.value = MostUsedCategoryUiState(
                    labels = labels,
                    isLoading = false
                )
            }
        }
    }

    private fun getCategoryIcon(category: TaskGroupCategory): DrawableResource {
        return when (category) {
            TaskGroupCategory.OfficeProject -> Res.drawable.briefcase
            TaskGroupCategory.PersonalProject -> Res.drawable.user_octagon
            TaskGroupCategory.DailyStudy -> Res.drawable.book
            TaskGroupCategory.Other -> Res.drawable.document_text
        }
    }

    private fun getDefaultLabels(): List<SphereLabel> {
        return listOf(
            SphereLabel("Office", Res.drawable.briefcase),
            SphereLabel("Personal", Res.drawable.user_octagon),
            SphereLabel("Books", Res.drawable.book),
            SphereLabel("Shopping", Res.drawable.calendar),
            SphereLabel("Health", Res.drawable.document_text),
            SphereLabel("Projects", Res.drawable.multicolored_smartphone_notifications),
            SphereLabel("Home", Res.drawable.home),
            SphereLabel("Travel", Res.drawable.close_up_of_pink_coffee_cup)
        )
    }
}

