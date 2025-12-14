package io.jadu.todoApp.ui.screens.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import io.jadu.todoApp.ui.route.NavRoute
import io.jadu.todoApp.ui.route.RootNavGraph
import io.jadu.todoApp.ui.screens.AboutUsPage
import io.jadu.todoApp.ui.screens.EditTodoScreen

@Composable
fun MainScreenNavigation(
    navController: NavHostController,
    startDestination: RootNavGraph = RootNavGraph.MainScreenNav
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<NavRoute.AboutUs> {
            AboutUsPage(navController)
        }

        composable<NavRoute.EditTodo> { backStackEntry ->
            val editTodoRoute = backStackEntry.toRoute<NavRoute.EditTodo>()
            EditTodoScreen(
                navController = navController,
                todoId = editTodoRoute.todoId
            )
        }
    }
}
