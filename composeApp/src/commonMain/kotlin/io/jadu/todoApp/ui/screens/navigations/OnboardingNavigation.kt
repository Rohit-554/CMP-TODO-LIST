package io.jadu.todoApp.ui.screens.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.jadu.todoApp.ui.route.NavRoute

/**
 * Onboarding Navigation Graph
 * Handles all onboarding related screens
 */
@Composable
fun OnboardingNavigation(
    navController: NavHostController,
    onOnboardingComplete: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Onboarding
    ) {
        composable<NavRoute.Onboarding> {
            OnboardingScreen(
                onOnboardingComplete = onOnboardingComplete
            )
        }
    }
}

