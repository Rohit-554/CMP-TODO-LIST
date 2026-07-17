package io.jadu.todoApp.ui.screens.navigations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import io.jadu.todoApp.ui.animatedBottomBar.CurvedBottomNavigation
import io.jadu.todoApp.ui.animatedBottomBar.models.CurveAnimationType
import io.jadu.todoApp.ui.route.NavRoute
import io.jadu.todoApp.ui.screens.AboutUsScreen
import io.jadu.todoApp.ui.screens.AddProject
import io.jadu.todoApp.ui.screens.EditTodoScreen
import io.jadu.todoApp.ui.screens.SettingsScreen
import io.jadu.todoApp.ui.screens.TaskScreen
import io.jadu.todoApp.ui.screens.TestScreen
import io.jadu.todoApp.ui.screens.homescreen.HomeScreen
import io.jadu.todoApp.ui.screens.homescreen.MostUsedCategoryScreen
import io.jadu.todoApp.ui.screens.homescreen.components.CustomSnackbarHost
import io.jadu.todoApp.ui.screens.homescreen.components.getNavItems
import io.jadu.todoApp.ui.theme.TodoColors

@Composable
fun BottomBarNavigation() {
    val navigationState = rememberNavigationState(
        startRoute = NavRoute.Home,
        topLevelRoutes = listOf(
            NavRoute.Home,
            NavRoute.TaskScreen,
            NavRoute.AddProject,
            NavRoute.CategorySphereScreen,
            NavRoute.SettingsScreen
        )
    )
    val navigator = remember { Navigator(navigationState) }

    val bottomNavItems = getNavItems()

    val selectedIndex by remember {
        derivedStateOf {
            bottomNavItems
                .indexOfFirst { item -> navigationState.topLevelRoute == item.route }
                .coerceAtLeast(0)
        }
    }

    val entryProvider = entryProvider<NavKey> {
        entry<NavRoute.Home> {
            HomeScreen(
                onNavigateToTaskScreen = { navigator.navigate(NavRoute.TaskScreen) },
                onNavigateToEditTask = { id -> navigator.navigate(NavRoute.EditTodo(id)) }
            )
        }
        entry<NavRoute.TaskScreen> {
            TaskScreen(onNavigateToEditTask = { id ->
                navigator.navigate(
                    NavRoute.EditTodo(id)
                )
            }, onBack = { navigator.goBack() })
        }
        entry<NavRoute.AddProject> {
            AddProject(
                onBack = { navigator.goBack() },
                onSaveSuccess = { navigator.navigate(NavRoute.TaskScreen) }
            )
        }
        entry<NavRoute.CategorySphereScreen> { MostUsedCategoryScreen(onBack = { navigator.goBack() }) }
        entry<NavRoute.SettingsScreen> {
            SettingsScreen(
                onNavigateToAboutUs = { navigator.navigate(NavRoute.AboutUsScreen) },
                onBack = { navigator.goBack() })
        }
        entry<NavRoute.EditTodo> { editRoute ->
            EditTodoScreen(
                todoId = editRoute.todoId,
                onBack = { navigator.goBack() }
            )
        }
        entry<NavRoute.AboutUsScreen> { AboutUsScreen(onBack = { navigator.goBack() }) }
        entry<NavRoute.TestScreen> { TestScreen() }
    }

    Surface(
        modifier = Modifier.navigationBarsPadding(),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CustomSnackbarHost(
                modifier =
                    Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 48.dp)
                        .zIndex(99999f)
            )

            NavDisplay(
                entries = navigationState.toEntries(entryProvider),
                onBack = { navigator.goBack() },
                modifier = Modifier.fillMaxSize()
            )

            val isBottomBarVisible = navigationState.currentRoute in listOf(
                NavRoute.Home,
                NavRoute.TaskScreen,
                NavRoute.AddProject,
                NavRoute.CategorySphereScreen,
                NavRoute.SettingsScreen
            )

            AnimatedVisibility(
                visible = isBottomBarVisible,
                enter = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(durationMillis = 300)
                ) + fadeIn(),
                exit = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(durationMillis = 300)
                ) + fadeOut(),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                CurvedBottomNavigation(
                    items = bottomNavItems,
                    selectedIndex = selectedIndex,
                    onItemSelected = { index ->
                        val selectedRoute = bottomNavItems[index].route

                        if (index == 0) {
                            navigator.navigate(NavRoute.Home)
                            return@CurvedBottomNavigation
                        }

                        if (index != selectedIndex) {
                            navigator.navigate(selectedRoute)
                        }
                    },
                    curveAnimationType = CurveAnimationType.SMOOTH,
                    enableHapticFeedback = true,
                    showLabels = true,
                    navBarBackgroundColor = TodoColors.LightPrimary.color,
                    fabBackgroundColor = TodoColors.Primary.color,
                    unselectedIconTint = TodoColors.Primary.color,
                )
            }
        }
    }
}
