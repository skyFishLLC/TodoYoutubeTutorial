package dev.skyfish.todo.feature_todo.domain.use_case

import dev.skyfish.todo.feature_todo.domain.model.TodoItem
import dev.skyfish.todo.feature_todo.domain.repo.TodoListRepo
import javax.inject.Inject

class GetTodoItemFromRemoteUseCase @Inject constructor(
    private val repo: TodoListRepo
) {
    suspend operator fun invoke(id: Int): TodoItem?{
        return repo.getSingleTodoItemById(id)
    }
}