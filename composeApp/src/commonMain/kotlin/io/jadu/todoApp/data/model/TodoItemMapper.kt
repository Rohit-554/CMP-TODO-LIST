package io.jadu.todoApp.data.model

/**
 * Extension function to convert TodoItem to ScheduleTaskModel for UI display
 */
fun TodoItem.toScheduleTaskModel(): ScheduleTaskModel {
    // If scheduledTime is empty but scheduledDate exists, create a time range
    val displayTime = if (scheduledTime.isNullOrBlank() && !scheduledDate.isNullOrBlank()) {
        formatTimeFromDate(scheduledDate)
    } else {
        scheduledTime ?: "No time set"
    }

    return ScheduleTaskModel(
        id = this.id.toString(),
        title = this.title,
        description = this.description.ifBlank { this.groupCategory.displayName },
        scheduledTime = displayTime,
        category = this.groupCategory,
        status = this.status
    )
}

/**
 * Helper function to format time from date string
 * Assumes date format like "2024-12-05" and creates a time range
 */
private fun formatTimeFromDate(dateString: String): String {
    return try {
        "9:00 AM - 10:00 AM" // Default time range
    } catch (e: Exception) {
        "All Day"
    }
}

/**
 * Extension function to convert a list of TodoItems to ScheduleTaskModels
 */
fun List<TodoItem>.toScheduleTaskModels(): List<ScheduleTaskModel> {
    return this.map { it.toScheduleTaskModel() }
}

