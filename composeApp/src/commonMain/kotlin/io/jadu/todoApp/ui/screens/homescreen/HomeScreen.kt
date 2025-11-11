package io.jadu.todoApp.ui.screens.homescreen

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import io.jadu.todoApp.ui.animatedBottomBar.CurvedBottomNavigation
import io.jadu.todoApp.ui.screens.TodoBackgroundScreen
import io.jadu.todoApp.ui.screens.homescreen.components.HomePageContent
import io.jadu.todoApp.ui.screens.homescreen.components.navItems
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen() {
    var selectedIndex by remember { mutableStateOf(0) }
    Scaffold (
        bottomBar = {
            CurvedBottomNavigation(
                modifier = Modifier,
                items = navItems,
                selectedIndex = selectedIndex,
                showDot = false,
                enableFabIconScale = true,
                enableHapticFeedback = false
            ) { index ->
                selectedIndex = index
            }
        }
    ) {
        TodoBackgroundScreen {
            HomePageContent()
        }
    }
}