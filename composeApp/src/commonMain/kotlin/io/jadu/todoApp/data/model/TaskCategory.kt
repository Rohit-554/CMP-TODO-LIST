package io.jadu.todoApp.data.model

import androidx.compose.ui.graphics.Color
import io.jadu.todoApp.ui.theme.TodoColors

enum class TaskCategory(
    val displayName: String,
    val backgroundColor: Color,
    val progressColor: Color
) {
    Office(
        displayName = "Office",
        backgroundColor = Color(0xFFF0F4FF),
        progressColor = Color(0xFF0066FF)
    ),
    Personal(
        displayName = "Personal",
        backgroundColor = Color(0xFFFFF0E6),
        progressColor = Color(0xFFFF6B35)
    ),
    Study(
        displayName = "Study",
        backgroundColor = Color(0xFFFFF8F0),
        progressColor = Color(0xFFFFB800)
    ),
    Other(
        displayName = "Other",
        backgroundColor = Color(0xFFF5F5F5),
        progressColor = TodoColors.Primary.color
    )
}