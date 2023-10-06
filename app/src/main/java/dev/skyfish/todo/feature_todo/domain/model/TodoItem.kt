package dev.skyfish.todo.feature_todo.domain.model

data class TodoItem(
    val title: String,
    val description: String,
    val timestamp: Long,
    val completed: Boolean,
    val archived: Boolean,
    val id: Int?
)
