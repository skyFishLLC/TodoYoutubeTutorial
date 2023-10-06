package dev.skyfish.todo.feature_todo.domain.use_case

import dev.skyfish.todo.feature_todo.domain.model.TodoItem
import dev.skyfish.todo.feature_todo.domain.util.OrderDirection
import dev.skyfish.todo.feature_todo.domain.util.TodoItemOrder

class GetTodoItemsFromRemoteArchiveFilteredUseCase(
        private val getSortedTodoItemsUseCase: GetSortedTodoItemsUseCase
    ) {
        suspend operator fun invoke(
            todoItemOrder: TodoItemOrder = TodoItemOrder.Time(OrderDirection.Down),
            showArchived: Boolean
        ): List<TodoItem> {
            return if(showArchived){
                getSortedTodoItemsUseCase(todoItemOrder)
            }else{
                getSortedTodoItemsUseCase(todoItemOrder).filter{ !it.archived}
            }
        }
    }