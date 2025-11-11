package io.jadu.todoApp.ui.screens.homescreen.components

import io.jadu.todoApp.ui.animatedBottomBar.models.IconSource
import io.jadu.todoApp.ui.animatedBottomBar.models.NavItem
import io.jadu.todoApp.ui.navigation.NavRoute
import todo_list.composeapp.generated.resources.Res
import todo_list.composeapp.generated.resources.add
import todo_list.composeapp.generated.resources.calendar
import todo_list.composeapp.generated.resources.document_text
import todo_list.composeapp.generated.resources.home
import todo_list.composeapp.generated.resources.profile_2user

val navItems = listOf(
    NavItem(
        icon = IconSource.Drawable(Res.drawable.home),
        selectedIcon = IconSource.Drawable(Res.drawable.home),
        label = "Home",
        route = NavRoute.Home
    ),

    NavItem(
        icon = IconSource.Drawable(Res.drawable.calendar),
        selectedIcon = null,
        label = "Todos",
        route = NavRoute.TodoList
    ),

    NavItem(
        icon = IconSource.Drawable(Res.drawable.add),
        selectedIcon = null,
        label = "Add",
        route = NavRoute.TodoList
    ),

    NavItem(
        icon = IconSource.Drawable(Res.drawable.document_text),
        selectedIcon = null,
        label = "Favourites",
        route = NavRoute.TodoList
    ),
    NavItem(
        icon = IconSource.Drawable(Res.drawable.profile_2user),
        selectedIcon = null,
        label = "Profile",
        route = NavRoute.TodoList
    )
)