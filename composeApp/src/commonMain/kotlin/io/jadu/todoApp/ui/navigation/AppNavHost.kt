package io.jadu.todoApp.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(startDestination: NavRoute = NavRoute.SplashScreen, navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<NavRoute.SplashScreen> { abc() }
        composable<NavRoute.DashboardScreen> {  }
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