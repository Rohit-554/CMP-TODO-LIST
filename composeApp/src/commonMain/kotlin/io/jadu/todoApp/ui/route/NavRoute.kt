package io.jadu.todoApp.ui.route

import kotlinx.serialization.Serializable

@Serializable
sealed class NavRoute {

    @Serializable
    data object SplashScreen : NavRoute()

    @Serializable
    data object Onboarding : NavRoute()

    @Serializable
    data object TodoList : NavRoute()

    @Serializable
    data object Home : NavRoute()

    @Serializable
    data object TaskScreen : NavRoute()

    @Serializable
    data object AddProject: NavRoute()

    @Serializable
    data object SettingsPage : NavRoute()

    @Serializable
    data object AboutUs : NavRoute()

    @Serializable
    data class EditTodo(val todoId: Long) : NavRoute()

    @Serializable
    data object TestScreen : NavRoute()
}

/**
 * Root level navigation graph routes
 */
@Serializable
sealed class RootNavGraph {
    @Serializable
    data object Onboarding : RootNavGraph()

    @Serializable
    data object BottomNavBar : RootNavGraph()

    @Serializable
    data object MainScreenNav : RootNavGraph()
}