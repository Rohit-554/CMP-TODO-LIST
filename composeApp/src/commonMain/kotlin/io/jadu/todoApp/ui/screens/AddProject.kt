package io.jadu.todoApp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import io.jadu.todoApp.data.model.TaskGroupCategory
import io.jadu.todoApp.ui.animatedBottomBar.CurvedBottomNavigation
import io.jadu.todoApp.ui.components.CarouselCalendar
import io.jadu.todoApp.ui.components.CurvedButton
import io.jadu.todoApp.ui.components.DatePickerDialog
import io.jadu.todoApp.ui.components.EditDetailCard
import io.jadu.todoApp.ui.components.SelectGroupBottomSheet
import io.jadu.todoApp.ui.components.SelectionCard
import io.jadu.todoApp.ui.components.SelectionCardConfig
import io.jadu.todoApp.ui.components.TodoTopAppBar
import io.jadu.todoApp.ui.screens.homescreen.components.navItems
import io.jadu.todoApp.ui.theme.BodyLarge
import io.jadu.todoApp.ui.theme.BodyXLarge
import io.jadu.todoApp.ui.theme.BodyXSmall
import io.jadu.todoApp.ui.theme.Spacing
import io.jadu.todoApp.ui.theme.TodoColors
import io.jadu.todoApp.ui.uiutils.VSpacer
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import todo_list.composeapp.generated.resources.Res
import todo_list.composeapp.generated.resources.calendar

@Composable
@Preview
fun AddProject(navController: NavHostController = rememberNavController()) {
    var openTaskGroupSelection by remember { mutableStateOf(false) }
    var textChange by remember { mutableStateOf("") }
    var textDescription by remember { mutableStateOf("") }
    var selectedCategoryGroup by remember { mutableStateOf(TaskGroupCategory.DailyStudy) }
    var openDatePicker by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    TodoBackgroundScreen {

        // Don't give here, padding as topAppBar already have
        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Appbar
            TodoTopAppBar(
                title = "Add Project",
                modifier = Modifier.systemBarsPadding(),
                navController = navController
            )

            // This column is used to give padding to components included
            Column (
                modifier = Modifier.fillMaxWidth().padding(Spacing.s4),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Add TaskGroup
                SelectionCard (
                    cardConfig = SelectionCardConfig (
                        title = "Task Group",
                        subtitle = "Work",
                        leadingIcon = {
                            Box(
                                modifier = Modifier
                                    .size(Spacing.s8)
                                    .background(
                                        color = selectedCategoryGroup.color.copy(alpha = 0.15f),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = selectedCategoryGroup.icon,
                                    contentDescription = selectedCategoryGroup.displayName,
                                    tint = selectedCategoryGroup.color,
                                    modifier = Modifier.size(Spacing.s4)
                                )
                            }
                        }
                    )
                ) {
                    scope.launch {
                        openTaskGroupSelection = !openTaskGroupSelection
                    }

                }

                // Add Project Name
                EditDetailCard(
                    value = textChange,
                    placeHolderText = "Uber Designing",
                    textStyle = BodyXLarge(),
                    onTextChange = {
                        textChange = it
                    }
                )

                // Add description
                EditDetailCard(
                    value = textDescription,
                    title = "Description",
                    placeHolderText = "Add Project description here",
                    onTextChange = { alphabet ->
                        textDescription = alphabet
                    }
                )

                // Start date
                SelectionCard (
                    cardConfig = SelectionCardConfig (
                        title = "Start Date",
                        subtitle = "17 Nov, 2025",
                        leadingIcon = {
                            Image(
                                painter = painterResource(Res.drawable.calendar),
                                contentDescription = "",
                                colorFilter = ColorFilter.tint(
                                    color = TodoColors.Primary.color
                                )
                            )
                        }
                    )
                ) {
                    scope.launch {
                        openDatePicker = !openDatePicker
                    }
                }

                // End date
                SelectionCard (
                    cardConfig = SelectionCardConfig (
                        title = "End Date",
                        subtitle = "20 Nov, 2025",
                        leadingIcon = {
                            Image(
                                painter = painterResource(Res.drawable.calendar),
                                contentDescription = "",
                                colorFilter = ColorFilter.tint(
                                    color = TodoColors.Primary.color
                                )
                            )
                        }
                    )
                ) {
                    scope.launch {
                        openDatePicker = !openDatePicker
                    }
                }

                // Take all the available Space below
                Spacer(modifier = Modifier.weight(1f))


                CurvedButton (
                    modifier = Modifier,
                    text = "Add Project"
                ) {  }
            }
        }

        if (openTaskGroupSelection)
            SelectGroupBottomSheet(
                onDismiss = {
                    openTaskGroupSelection = false
                }
            )

        if(openDatePicker)
            DatePickerDialog(
                onDateSelected = {},
                onDismiss = {
                    openDatePicker = false
                }
            )
    }
}