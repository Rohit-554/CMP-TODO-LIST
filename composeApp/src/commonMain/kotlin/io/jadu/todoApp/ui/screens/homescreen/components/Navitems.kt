package io.jadu.todoApp.ui.screens.homescreen.components

import io.jadu.todoApp.ui.animatedBottomBar.models.IconSource
import io.jadu.todoApp.ui.animatedBottomBar.models.NavItem
import io.jadu.todoApp.ui.navigation.NavRoute
import todo_list.composeapp.generated.resources.Res
import todo_list.composeapp.generated.resources.add
import todo_list.composeapp.generated.resources.briefcase
import todo_list.composeapp.generated.resources.calendar
import todo_list.composeapp.generated.resources.home
import todo_list.composeapp.generated.resources.user_octagon

val navItems = listOf(
    NavItem(
        icon = IconSource.Drawable(Res.drawable.home),
        label = "Home",
        route = NavRoute.Home
    ),
    NavItem(
        icon = IconSource.Drawable(Res.drawable.briefcase),
        label = "Tasks",
        route = NavRoute.TaskScreen
    ),
    NavItem(
        icon = IconSource.Drawable(Res.drawable.add),
        selectedIcon = IconSource.Drawable(Res.drawable.add),
        label = "Add",
        route = NavRoute.AddProject
    ),
    NavItem(
        icon = IconSource.Drawable(Res.drawable.calendar),
        label = "Calendar",
        route = NavRoute.SplashScreen
    ),
    NavItem(
        icon = IconSource.Drawable(Res.drawable.user_octagon),
        label = "Settings",
        route = NavRoute.SettingsPage
    )
)