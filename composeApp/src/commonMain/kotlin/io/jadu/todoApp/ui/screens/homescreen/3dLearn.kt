package io.jadu.todoApp.ui.screens.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import io.jadu.todoApp.ui.components.SphereTextDemo
import io.jadu.todoApp.ui.components.TodoTopAppBar
import io.jadu.todoApp.ui.screens.ColoredDot
import io.jadu.todoApp.ui.screens.TodoBackgroundScreen
import io.jadu.todoApp.ui.theme.Spacing
import io.jadu.todoApp.ui.theme.TodoColors
import io.jadu.todoApp.ui.viewModel.MostUsedCategoryViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import todo_list.composeapp.generated.resources.Res
import todo_list.composeapp.generated.resources.blue_desk_calendar
import todo_list.composeapp.generated.resources.blue_stopwatch_with_pink_arrow
import todo_list.composeapp.generated.resources.close_up_of_pink_coffee_cup
import todo_list.composeapp.generated.resources.multicolored_smartphone_notifications
import todo_list.composeapp.generated.resources.pie_chart
import kotlin.random.Random


@Composable
@Preview
fun MostUsedCategoryScreen(
    navHostController: NavHostController,
    viewModel: MostUsedCategoryViewModel = koinInject()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TodoTopAppBar(
                modifier = Modifier.systemBarsPadding(),
                title = "Most Used Categories",
                navController = navHostController
            )
        }
    ) {
        TodoBackgroundScreen(
            shouldShowDotsAndIcons = false
        ) {
            // Random colored dots scattered across the screen
            val dotColors = listOf(
                TodoColors.Pink.color,
                TodoColors.Blue.color,
                TodoColors.Emerald.color,
                TodoColors.Orange.color,
                TodoColors.Orchid.color,
                TodoColors.Turquoise.color,
                TodoColors.Gold.color,
                TodoColors.DarkPurple.color
            )

            val randomDots = remember {
                List(15) {
                    Triple(
                        dotColors.random(),
                        Random.nextFloat(),
                        Random.nextFloat()
                    )
                }
            }

            randomDots.forEach { (color, xPercent, yPercent) ->
                ColoredDot(
                    color = color,
                    x = (maxWidth.value * xPercent).dp,
                    y = (maxHeight.value * yPercent).dp,
                    size = (Spacing.s1.value + Random.nextInt(6)).dp
                )
            }

            // Top left
            Image(
                painter = painterResource(Res.drawable.blue_stopwatch_with_pink_arrow),
                contentDescription = null,
                modifier = Modifier
                    .offset(x = (maxWidth.value * 0.1f).dp, y = (maxHeight.value * 0.08f).dp)
                    .size(45.dp)
                    .rotate(-10f)
            )

            // Top right
            Image(
                painter = painterResource(Res.drawable.pie_chart),
                contentDescription = null,
                modifier = Modifier
                    .offset(x = (maxWidth.value * 0.78f).dp, y = (maxHeight.value * 0.12f).dp)
                    .size(42.dp)
                    .rotate(15f)
            )

            // Center right
            Image(
                painter = painterResource(Res.drawable.blue_desk_calendar),
                contentDescription = null,
                modifier = Modifier
                    .offset(x = (maxWidth.value * 0.82f).dp, y = (maxHeight.value * 0.45f).dp)
                    .size(48.dp)
                    .rotate(-12f)
            )

            // Bottom left
            Image(
                painter = painterResource(Res.drawable.multicolored_smartphone_notifications),
                contentDescription = null,
                modifier = Modifier
                    .offset(x = (maxWidth.value * 0.08f).dp, y = (maxHeight.value * 0.78f).dp)
                    .size(50.dp)
                    .rotate(8f)
            )

            // Bottom right
            Image(
                painter = painterResource(Res.drawable.close_up_of_pink_coffee_cup),
                contentDescription = null,
                modifier = Modifier
                    .offset(x = (maxWidth.value * 0.75f).dp, y = (maxHeight.value * 0.82f).dp)
                    .size(46.dp)
                    .rotate(-18f)
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                SphereTextDemo(
                    labels = uiState.labels
                )
            }

        }
    }
}
