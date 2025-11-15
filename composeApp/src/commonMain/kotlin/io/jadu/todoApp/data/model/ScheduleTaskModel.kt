package io.jadu.todoApp.data.model

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class ScheduleTaskModel(
    val id: String = Uuid.random().toString(),
    val title: String = "Grocery shopping app design",
    val description: String = "Market Research",
    val scheduledTime: String = "05:00AM",
    val category: TaskGroupCategory = TaskGroupCategory.DailyStudy,
    val status: TaskStatus = TaskStatus.IN_PROGRESS
)

enum class TaskStatus {
    IN_PROGRESS,
    TO_DO,
    DONE
}