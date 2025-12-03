package io.jadu.todoApp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.jadu.todoApp.data.model.TodoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert
    suspend fun insert(item: TodoItem)

    @Query("SELECT count(*) FROM TodoItem")
    suspend fun count(): Int

    @Query("SELECT * FROM todoitem")
    fun getAllAsFlow(): Flow<List<TodoItem>>
}