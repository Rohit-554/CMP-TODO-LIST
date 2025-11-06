package io.jadu.todoApp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.jadu.todoApp.data.model.TodoItem
import io.jadu.todoApp.domain.TodoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoViewModel (
    private val repository: TodoRepository
) : ViewModel() {

    //flows - stream of data - observe
    private val _todos = MutableStateFlow<List<TodoItem>>(emptyList())
    val todos : StateFlow<List<TodoItem>> = _todos

    private fun loadTodos() {
        viewModelScope.launch {
            _todos.value = repository.getAllTodo()
        }
    }

    fun addTodo(todo: TodoItem) {
        viewModelScope.launch {
            repository.addTodo(todo)
            _todos.value = repository.getAllTodo()
        }
    }

    fun deleTodo(todo: TodoItem) {
        viewModelScope.launch {
            repository.deleteTodo(todo)
            _todos.value = repository.getAllTodo()
        }
    }
}