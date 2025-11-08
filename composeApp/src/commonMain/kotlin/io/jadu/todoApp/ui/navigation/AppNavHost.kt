package io.jadu.todoApp.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.jadu.todoApp.ui.screens.OnboardingScreen

@Composable
fun AppNavHost(startDestination: NavRoute = NavRoute.Onboarding, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<NavRoute.SplashScreen> { abc() }
        composable<NavRoute.Onboarding> { OnboardingScreen() }
        composable<NavRoute.HomeScreen> {  }
        composable<NavRoute.TodoList> {  }
    }
}

@Composable
fun abc() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            "Hello blockbusters!!",
            style = MaterialTheme.typography.headlineLarge
        )
    }

}