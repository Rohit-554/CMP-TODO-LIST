package io.jadu.todoApp.ui.screens.homescreen

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import io.jadu.todoApp.ui.screens.TodoBackgroundScreen
import io.jadu.todoApp.ui.screens.homescreen.components.HomePageContent

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold {
        TodoBackgroundScreen {
            HomePageContent(navController)
        }
    }
}