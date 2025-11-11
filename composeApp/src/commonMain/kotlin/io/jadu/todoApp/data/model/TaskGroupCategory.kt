package io.jadu.todoApp.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import io.jadu.todoApp.ui.theme.TodoColors

enum class TaskGroupCategory(
    val displayName: String,
    val color: Color,
    val icon: ImageVector
) {
    OfficeProject(
        displayName = "Office Project",
        color = Color(0xFF0066FF),
        icon = Icons.Default.Work
    ),
    PersonalProject(
        displayName = "Personal Project",
        color = Color(0xFFFF6B35),
        icon = Icons.Default.Person
    ),
    DailyStudy(
        displayName = "Daily Study",
        color = Color(0xFFFFB800),
        icon = Icons.Default.School
    ),
    Other(
        displayName = "Other Tasks",
        color = TodoColors.Primary.color,
        icon = Icons.AutoMirrored.Filled.List
    )
}