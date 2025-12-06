package io.jadu.todoApp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.jadu.todoApp.data.model.TodoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TodoItem)

    @Update
    suspend fun update(item: TodoItem)

    @Delete
    suspend fun delete(item: TodoItem)

    @Query("DELETE FROM TodoItem WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM TodoItem")
    fun getAllAsFlow(): Flow<List<TodoItem>>

    @Query("SELECT * FROM TodoItem")
    suspend fun getAll(): List<TodoItem>

    @Query("SELECT * FROM TodoItem WHERE id = :id")
    suspend fun getById(id: Long): TodoItem?

    @Query("SELECT COUNT(*) FROM TodoItem")
    suspend fun count(): Int

    @Query("SELECT COUNT(*) FROM TodoItem WHERE completed = 1")
    fun getCompletedCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM TodoItem WHERE completed = 0")
    fun getIncompleteCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM TodoItem")
    fun getTotalCount(): Flow<Int>

    @Query("SELECT DISTINCT groupCategory FROM TodoItem WHERE groupCategory IS NOT NULL")
    fun getUniqueGroupCategories(): Flow<List<String>>
}