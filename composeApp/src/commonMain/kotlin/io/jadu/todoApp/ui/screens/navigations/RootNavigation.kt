package io.jadu.todoApp.ui.screens.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.jadu.todoApp.ui.route.RootNavGraph


/**
 * Root Navigation that decides between Onboarding and BottomNavBar app flow
 */
@Composable
fun RootNavigation(
    navController: NavHostController,
    startDestination: RootNavGraph
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Onboarding Navigation Graph
        composable<RootNavGraph.Onboarding> {
            val onboardingNavController = rememberNavController()
            OnboardingNavigation(
                navController = onboardingNavController,
                onOnboardingComplete = {
                    navController.navigate(RootNavGraph.BottomNavBar) {
                        popUpTo(RootNavGraph.Onboarding) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // BottomNavBar App Navigation Graph
        composable<RootNavGraph.BottomNavBar> {
            val bottomNavBarController = rememberNavController()
            BottomBarNavigation(navController = bottomNavBarController)
        }

        composable<RootNavGraph.MainScreenNav> {
            MainScreenNavigation(rememberNavController())
        }
    }
}

