package io.jadu.todoApp.ui.screens.homescreen

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import io.jadu.todoApp.ui.screens.homescreen.components.HomePageContent
import io.jadu.todoApp.ui.screens.onBoarding.TodoBackgroundScreen

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold {
        TodoBackgroundScreen {
            HomePageContent(navController)
        }
    }
}