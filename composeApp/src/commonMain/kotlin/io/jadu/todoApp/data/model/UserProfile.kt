package io.jadu.todoApp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserProfile")
data class UserProfile(
    @PrimaryKey
    val id: Int = 1, // Only one profile per user
    val name: String = "",
    val photoData: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as UserProfile

        if (id != other.id) return false
        if (name != other.name) return false
        if (photoData != null) {
            if (other.photoData == null) return false
            if (!photoData.contentEquals(other.photoData)) return false
        } else if (other.photoData != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + (photoData?.contentHashCode() ?: 0)
        return result
    }
}

