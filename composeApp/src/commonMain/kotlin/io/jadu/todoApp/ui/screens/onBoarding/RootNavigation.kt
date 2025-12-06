package io.jadu.todoApp.ui.screens.onBoarding

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.jadu.todoApp.ui.navigation.RootNavGraph
import io.jadu.todoApp.ui.screens.MainNavigation
import io.jadu.todoApp.ui.theme.TodoColors


/**
 * Root Navigation that decides between Onboarding and Main app flow
 */
@Composable
fun RootNavigation(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Onboarding Navigation Graph
        composable(RootNavGraph.Onboarding.route) {
            val onboardingNavController = rememberNavController()
            OnboardingNavigation(
                navController = onboardingNavController,
                onOnboardingComplete = {
                    navController.navigate(RootNavGraph.Main.route) {
                        popUpTo(RootNavGraph.Onboarding.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Main App Navigation Graph
        composable(RootNavGraph.Main.route) {
            val mainNavController = rememberNavController()
            Surface(
                color = TodoColors.LightPrimary.color
            ) {
                MainNavigation(navController = mainNavController)
            }
        }
    }
}

