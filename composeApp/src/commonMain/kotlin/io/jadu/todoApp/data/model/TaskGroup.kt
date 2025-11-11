package io.jadu.todoApp.data.model

data class TaskGroup(
    val id: String,
    val category: TaskGroupCategory,
    val taskCount: Int,
    val completionPercentage: Int
)