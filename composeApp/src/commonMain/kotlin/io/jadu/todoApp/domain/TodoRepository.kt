package io.jadu.todoApp.domain

import io.jadu.todoApp.data.model.TodoItem

interface TodoRepository {
    suspend fun getAllTodo() : List<TodoItem>
    suspend fun addTodo(todo : TodoItem)
    suspend fun deleteTodo(todo: TodoItem)
}