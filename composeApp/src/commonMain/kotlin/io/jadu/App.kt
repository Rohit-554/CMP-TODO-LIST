package io.jadu


import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import io.jadu.todoApp.ui.screens.MainScreen
import io.jadu.todoApp.ui.theme.TodoAppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    TodoAppTheme {
        val navController = rememberNavController()
        MainScreen(navController)
    }
}