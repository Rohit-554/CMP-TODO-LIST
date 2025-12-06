package io.jadu.todoApp.ui.viewModel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import io.jadu.todoApp.ui.navigation.NavRoute
import kotlinx.coroutines.flow.first

class OnBoardingViewModel(
    val prefs: DataStore<Preferences>
) : ViewModel() {

    private val isNewUserKey = booleanPreferencesKey("is_new_user")

/*    val currentRoute = mutableStateOf<NavRoute>(NavRoute.Home)
    init {
        viewModelScope.launch {
            currentRoute.value = getCurrentRoute()
        }
    }*/

    suspend fun getCurrentRoute(): NavRoute {
        val isNewUser = prefs.data.first()[isNewUserKey] ?: true
        return if (isNewUser) NavRoute.Onboarding else NavRoute.Home
    }

    suspend fun setUserStatus(isFirstTime: Boolean): Boolean {
        prefs.edit { it[isNewUserKey] = isFirstTime }
        return true
    }

}