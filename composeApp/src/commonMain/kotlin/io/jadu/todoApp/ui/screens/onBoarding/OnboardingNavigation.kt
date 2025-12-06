package io.jadu.todoApp.ui.screens.onBoarding

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.jadu.todoApp.ui.navigation.NavRoute

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

