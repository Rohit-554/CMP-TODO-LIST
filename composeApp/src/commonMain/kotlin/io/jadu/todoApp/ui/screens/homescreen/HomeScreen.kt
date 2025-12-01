package io.jadu.todoApp.ui.screens.homescreen

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import io.jadu.todoApp.ui.screens.TodoBackgroundScreen
import io.jadu.todoApp.ui.screens.homescreen.components.HomePageContent

@Composable
fun HomeScreen() {
    Scaffold {
        TodoBackgroundScreen {
            HomePageContent()
        }
    }
}