package dev.skyfish.todo.feature_todo.domain.repo

import dev.skyfish.todo.feature_todo.domain.model.TodoItem

interface TodoListRepo {
    suspend fun getAllTodos(): List<TodoItem>
    suspend fun getAllTodosFromLocalCache(): List<TodoItem>
    suspend fun getAllTodosFromRemote()
    suspend fun getSingleTodoItemById(id: Int): TodoItem?
    suspend fun addTodoItem(todo:TodoItem)
    suspend fun updateTodoItem(todo: TodoItem)
    suspend fun deleteTodoItem(todo: TodoItem)
}