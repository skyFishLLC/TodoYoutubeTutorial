package dev.skyfish.todo.feature_todo.domain.use_case

import dev.skyfish.todo.core.util.TodoNewUpdateConstants
import dev.skyfish.todo.feature_todo.domain.model.TodoItem
import dev.skyfish.todo.feature_todo.domain.repo.TodoListRepo
import dev.skyfish.todo.feature_todo.domain.util.InvalidTodoItemException
import javax.inject.Inject

class AddTodoItemUseCase @Inject constructor(
    private val repo: TodoListRepo
) {
    suspend operator fun invoke(todo: TodoItem){
        if(todo.title.isBlank()){
            throw InvalidTodoItemException(TodoNewUpdateConstants.EMPTY_TITLE)
        }
        if(todo.description.isBlank()){
            throw InvalidTodoItemException(TodoNewUpdateConstants.EMPTY_DESCRIPTION)
        }
        repo.addTodoItem(todo)
    }
}