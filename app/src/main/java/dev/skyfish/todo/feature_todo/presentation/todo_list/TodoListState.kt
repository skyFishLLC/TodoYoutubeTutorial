package dev.skyfish.todo.feature_todo.presentation.todo_list

import dev.skyfish.todo.feature_todo.domain.model.TodoItem
import dev.skyfish.todo.feature_todo.domain.util.SortingDirection
import dev.skyfish.todo.feature_todo.domain.util.TodoItemOrder

data class TodoListState(
    val todoItems: List<TodoItem> = emptyList(),
    val todoItemOrder: TodoItemOrder, //= TodoItemOrder.Time(SortingDirection.Down, true),
    val isLoading: Boolean = true,
    val error: String? = null
)
