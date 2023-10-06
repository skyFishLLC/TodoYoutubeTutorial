package dev.skyfish.todo.feature_todo.domain.use_case

import javax.inject.Inject

data class TodoUseCases @Inject constructor(
    val getSortedTodoItemsUseCase: GetSortedTodoItemsUseCase,
    val deleteTodoItemUseCase: DeleteTodoItemUseCase,
    val addTodoItemUseCase: AddTodoItemUseCase,
    val toggleArchivedTodoItemUseCase: ToggleArchivedTodoItemUseCase,
    val toggleCompletedTodoItemUseCase: ToggleCompletedTodoItemUseCase,
    val getTodoItemsFromCacheUseCase: GetTodoItemsFromCacheUseCase,
    val getTodoItemFromRemoteUseCase: GetTodoItemFromRemoteUseCase,
    val updateTodoItemUseCase: UpdateTodoItemUseCase,
    val getTodoItemsFromCacheArchiveFilteredUseCase: GetTodoItemsFromCacheArchiveFilteredUseCase,
    val getTodoItemsFromRemoteArchiveFilteredUseCase: GetTodoItemsFromRemoteArchiveFilteredUseCase
)