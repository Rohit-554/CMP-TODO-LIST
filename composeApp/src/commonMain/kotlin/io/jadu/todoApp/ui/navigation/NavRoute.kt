package io.jadu.todoApp.ui.navigation

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
    data object TestScreen : NavRoute()
}