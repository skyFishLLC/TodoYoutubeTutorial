package dev.skyfish.todo.feature_todo.domain.use_case

import dev.skyfish.todo.feature_todo.domain.model.TodoItem
import dev.skyfish.todo.feature_todo.domain.repo.TodoListRepo
import dev.skyfish.todo.feature_todo.domain.util.OrderDirection
import dev.skyfish.todo.feature_todo.domain.util.TodoItemOrder

class GetTodoItemsFromCacheUseCase(
    private val repo: TodoListRepo
) {
    suspend operator fun invoke(
        todoItemOrder: TodoItemOrder = TodoItemOrder.Time(OrderDirection.Down)
    ): List<TodoItem>{
        val todos = repo.getAllTodosFromLocalCache()
        return when(todoItemOrder.orderDirection){
            is OrderDirection.Down -> {
                when(todoItemOrder){
                    is TodoItemOrder.Title -> todos.sortedByDescending { it.title.lowercase() }
                    is TodoItemOrder.Time -> todos.sortedByDescending { it.title.lowercase() }
                    is TodoItemOrder.Completed -> todos.sortedByDescending { it.title.lowercase() }
                }
            }
            is OrderDirection.Up -> {
                when(todoItemOrder){
                    is TodoItemOrder.Title -> todos.sortedBy { it.title.lowercase() }
                    is TodoItemOrder.Time -> todos.sortedBy { it.title.lowercase() }
                    is TodoItemOrder.Completed -> todos.sortedBy { it.title.lowercase() }
                }
            }
        }
    }
}