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
import io.jadu.todoApp.ui.screens.AddProject
import io.jadu.todoApp.ui.screens.OnboardingScreen
import io.jadu.todoApp.ui.screens.SettingsPage
import io.jadu.todoApp.ui.screens.TaskScreen
import io.jadu.todoApp.ui.screens.TestScreen
import io.jadu.todoApp.ui.screens.homescreen.HomeScreen

@Composable
fun AppNavHost(startDestination: NavRoute = NavRoute.SettingsPage, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<NavRoute.SplashScreen> { abc() }
        composable<NavRoute.Onboarding> { OnboardingScreen() }
        composable<NavRoute.Home> { HomeScreen() }
        composable<NavRoute.TaskScreen> { TaskScreen(navController) }
        composable<NavRoute.SettingsPage> { SettingsPage(navController) }
        composable<NavRoute.AddProject> { AddProject() }
        composable<NavRoute.TestScreen> { TestScreen() }
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