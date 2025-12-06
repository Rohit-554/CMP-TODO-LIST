package io.jadu.todoApp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.jadu.todoApp.data.local.TodoDao
import io.jadu.todoApp.data.local.UserProfileDao
import io.jadu.todoApp.data.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SettingsUiState(
    val userProfile: UserProfile = UserProfile(),
    val totalTodos: Int = 0,
    val completedTodos: Int = 0,
    val incompleteTodos: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class SettingsViewModel(
    private val userProfileDao: UserProfileDao,
    private val todoDao: TodoDao
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<SettingsUiState> = combine(
        userProfileDao.getUserProfile(),
        todoDao.getTotalCount(),
        todoDao.getCompletedCount(),
        todoDao.getIncompleteCount(),
        _isLoading,
        _errorMessage
    ) { flows ->
        val userProfile = flows[0] as? UserProfile
        val totalTodos = flows[1] as? Int ?: 0
        val completedTodos = flows[2] as? Int ?: 0
        val incompleteTodos = flows[3] as? Int ?: 0
        val isLoading = flows[4] as? Boolean ?: false
        val errorMessage = flows[5] as? String?

        SettingsUiState(
            userProfile = userProfile ?: UserProfile(),
            totalTodos = totalTodos,
            completedTodos = completedTodos,
            incompleteTodos = incompleteTodos,
            isLoading = isLoading,
            errorMessage = errorMessage
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SettingsUiState()
    )

    fun updateUserProfile(name: String, photoData: ByteArray?) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _errorMessage.value = null

                val profile = UserProfile(
                    id = 1,
                    name = name,
                    photoData = photoData
                )
                userProfileDao.insert(profile)

                _isLoading.value = false
            } catch (e: Exception) {
                _errorMessage.value = "Failed to save profile: ${e.message}"
                _isLoading.value = false
            }
        }
    }

    fun updateUserName(name: String) {
        viewModelScope.launch {
            try {
                val currentProfile = userProfileDao.getUserProfileOnce() ?: UserProfile()
                val updatedProfile = currentProfile.copy(name = name)
                userProfileDao.insert(updatedProfile)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to update name: ${e.message}"
            }
        }
    }

    fun updateUserPhoto(photoData: ByteArray?) {
        viewModelScope.launch {
            try {
                val currentProfile = userProfileDao.getUserProfileOnce() ?: UserProfile()
                val updatedProfile = currentProfile.copy(photoData = photoData)
                userProfileDao.insert(updatedProfile)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to update photo: ${e.message}"
            }
        }
    }
}

