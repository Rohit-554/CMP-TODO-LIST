package io.jadu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.jadu.todoApp.ui.navigation.AppNavHost
import io.jadu.todoApp.ui.theme.TodoAppTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import todo_list.composeapp.generated.resources.Res
import todo_list.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    initKoin()
    TodoAppTheme {
        val navController = rememberNavController()
        AppNavHost(
            navController = navController
        )

    }
}