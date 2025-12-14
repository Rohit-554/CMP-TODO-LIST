package io.jadu.todoApp.ui.screens.navigations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.jadu.todoApp.ui.animatedBottomBar.CurvedBottomNavigation
import io.jadu.todoApp.ui.animatedBottomBar.models.CurveAnimationType
import io.jadu.todoApp.ui.animatedBottomBar.models.IconSource
import io.jadu.todoApp.ui.animatedBottomBar.models.NavItem
import io.jadu.todoApp.ui.route.NavRoute
import io.jadu.todoApp.ui.screens.AddProject
import io.jadu.todoApp.ui.screens.SettingsPage
import io.jadu.todoApp.ui.screens.TaskScreen
import io.jadu.todoApp.ui.screens.TestScreen
import io.jadu.todoApp.ui.screens.homescreen.HomeScreen
import io.jadu.todoApp.ui.screens.homescreen.MostUsedCategoryScreen
import io.jadu.todoApp.ui.screens.homescreen.components.CustomSnackbarHost
import io.jadu.todoApp.ui.theme.TodoColors
import todo_list.composeapp.generated.resources.Res
import todo_list.composeapp.generated.resources.add
import todo_list.composeapp.generated.resources.briefcase
import todo_list.composeapp.generated.resources.calendar
import todo_list.composeapp.generated.resources.home
import todo_list.composeapp.generated.resources.user_octagon

@Composable
fun BottomBarNavigation(
    navController : NavHostController
) {
    val bottomNavItems = remember {
        listOf(
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
    }

    val currentBackStackEntry by navController.currentBackStackEntryFlow
        .collectAsState(initial = navController.currentBackStackEntry)

    val currentRoute = currentBackStackEntry?.destination?.route

    val selectedIndex = bottomNavItems.indexOfFirst { item ->
        currentRoute?.contains(item.route::class.simpleName ?: "") == true
    }.coerceAtLeast(0)

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
            NavHost(
                navController = navController,
                startDestination = NavRoute.Home,
                modifier = Modifier.fillMaxSize()
            ) {
                composable<NavRoute.Home> {
                    HomeScreen(navController)
                }

                composable<NavRoute.TaskScreen> {
                    TaskScreen(navController)
                }

                composable<NavRoute.AddProject> {
                    AddProject(navController)
                }

                composable<NavRoute.SplashScreen> {
                    MostUsedCategoryScreen(navController)
                }

                composable<NavRoute.SettingsPage> {
                    SettingsPage(navController)
                }

                composable<NavRoute.TestScreen> {
                    TestScreen()
                }
            }

            CurvedBottomNavigation(
                modifier = Modifier.align(Alignment.BottomCenter),
                items = bottomNavItems,
                selectedIndex = selectedIndex,
                onItemSelected = { index ->
                    val selectedRoute = bottomNavItems[index].route

                    if (index == 0) {
                        navController.navigate(NavRoute.Home) {
                            popUpTo(0) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                        return@CurvedBottomNavigation
                    }

                    if (index != selectedIndex) {
                        navController.navigate(selectedRoute) {
                            launchSingleTop = true
                            restoreState = true
                        }
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











