package io.jadu.todoApp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Entity(tableName = "TodoItem")
data class TodoItem(
    @PrimaryKey
    val id: Long = 0,
    val title: String,
    val description: String = "",
    val completed: Boolean = false,
    val status: TaskStatus = TaskStatus.TO_DO,
    val category: TaskCategory = TaskCategory.Other,
    val groupCategory: TaskGroupCategory = TaskGroupCategory.Other,
    // Progress tracking
    val progressPercentage: Float = 0f,

    // Scheduling
    val scheduledTime: String? = null,
    val scheduledDate: String? = null,

    // Timestamps
    val createdAt: Instant = Clock.System.now(),
    val updatedAt: Instant = Clock.System.now(),
    val completedAt: Instant? = null,

    // Additional metadata
    val icon: String? = null,
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val tags: List<String> = emptyList(),
    val notes: String? = null
)

/**
 * Task status enum to track the current state of a todo item
 */
enum class TaskStatus {
    TO_DO,
    IN_PROGRESS,
    DONE
}

/**
 * Priority levels for tasks
 */
enum class TaskPriority {
    LOW,
    MEDIUM,
    HIGH,
    URGENT
}


object TodoTypeConverters {

    @TypeConverters
    fun fromInstant(value: Instant): Long? = value?.toEpochMilliseconds()

    @TypeConverters
    fun toInstant(value: Long?) : Instant? = value?.let (Instant::fromEpochMilliseconds)

    @TypeConverter
    fun fromTaskStatus(value: TaskStatus?): String? = value?.name

    @TypeConverter
    fun toTaskStatus(value: String?): TaskStatus? = value?.let(TaskStatus::valueOf)

    @TypeConverter
    fun fromTaskCategory(value: TaskCategory?): String? = value?.name

    @TypeConverter
    fun toTaskCategory(value: String?): TaskCategory? = value?.let(TaskCategory::valueOf)

    @TypeConverter
    fun fromTaskGroupCategory(value: TaskGroupCategory?): String? = value?.name

    @TypeConverter
    fun toTaskGroupCategory(value: String?): TaskGroupCategory? = value?.let(TaskGroupCategory::valueOf)

    @TypeConverter
    fun fromTaskPriority(value: TaskPriority?): String? = value?.name

    @TypeConverter
    fun toTaskPriority(value: String?): TaskPriority? = value?.let(TaskPriority::valueOf)

    @TypeConverter
    fun fromTags(value: List<String>?): String = value?.joinToString(separator = "|") ?: ""

    @TypeConverter
    fun toTags(value: String): List<String> =
        if (value.isBlank()) emptyList() else value.split("|").map { it.trim() }


}