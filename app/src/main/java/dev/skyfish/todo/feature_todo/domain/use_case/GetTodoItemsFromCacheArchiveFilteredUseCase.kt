package dev.skyfish.todo.feature_todo.domain.use_case

import dev.skyfish.todo.feature_todo.domain.model.TodoItem
import dev.skyfish.todo.feature_todo.domain.util.OrderDirection
import dev.skyfish.todo.feature_todo.domain.util.TodoItemOrder

class GetTodoItemsFromCacheArchiveFilteredUseCase(
    private val getTodoItemsFromCacheUseCase: GetTodoItemsFromCacheUseCase
) {
    suspend operator fun invoke(
        todoItemOrder: TodoItemOrder = TodoItemOrder.Time(OrderDirection.Down),
        showArchived: Boolean
    ): List<TodoItem> {
        return if(showArchived){
            getTodoItemsFromCacheUseCase(todoItemOrder)
        }else{
            getTodoItemsFromCacheUseCase(todoItemOrder).filter{ !it.archived}
        }
    }
}