package dev.skyfish.todo.feature_todo.presentation.todo_new_update

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.skyfish.todo.core.util.NewUpdateStrings
import dev.skyfish.todo.feature_todo.data.di.IoDispatcher
import dev.skyfish.todo.feature_todo.domain.use_case.TodoUseCases
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoNewUpdateViewModel @Inject constructor(
    private val todoUseCases: TodoUseCases,
    savedStateHandle: SavedStateHandle,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _state = mutableStateOf(TodoNewUpdateState())
    val state: State<TodoNewUpdateState> = _state

    private val errorHandler = CoroutineExceptionHandler {_, e ->
        e.printStackTrace()
        _state.value = _state.value.copy(
            error = e.message,
            isLoading = false
        )
    }

    private var currentTodoId: Int? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent{
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveTodo: UiEvent()
        object Back: UiEvent()
    }

    init {
        savedStateHandle.get<Int>("todoId")?.let { id ->
            if(id != -1){
                viewModelScope.launch(dispatcher + errorHandler){
                    todoUseCases.getTodoItemById(id)?.also { todo ->
                        currentTodoId = id
                        _state.value = _state.value.copy(
                            todo = todo,
                            isLoading = false,
                            isTitleHintVisible = todo.title.isBlank(),
                            isDescriptionHintVisible = todo.description.isBlank()
                        )
                    }
                }
            }else{
                _state.value = _state.value.copy(
                    isLoading = false
                )
            }

        }
    }


    fun onEvent(event: TodoNewUpdateEvent){
        when(event){
            TodoNewUpdateEvent.Back -> {
                viewModelScope.launch (dispatcher + errorHandler){
                    _eventFlow.emit(UiEvent.Back)
                }
            }
            is TodoNewUpdateEvent.ChangedDescriptionFocus -> {
                val shouldDescriptionHintBeVisible = !event.focusState.isFocused && _state.value.todo.description.isBlank()
                _state.value = _state.value.copy(
                    isDescriptionHintVisible = shouldDescriptionHintBeVisible
                )
            }
            is TodoNewUpdateEvent.ChangedTitleFocus -> {
                val shouldTitleHintBeVisible = !event.focusState.isFocused && _state.value.todo.title.isBlank()
                _state.value = _state.value.copy(
                    isTitleHintVisible = shouldTitleHintBeVisible
                )
            }
            TodoNewUpdateEvent.Delete -> {
                viewModelScope.launch(dispatcher + errorHandler){
                    if(currentTodoId != null){
                        todoUseCases.deleteTodoItem(_state.value.todo)
                    }
                    _eventFlow.emit(UiEvent.Back)
                }
            }
            is TodoNewUpdateEvent.EnteredDescription -> {
                _state.value = _state.value.copy(
                    todo = _state.value.todo.copy(
                        description = event.value
                    )
                )
            }
            is TodoNewUpdateEvent.EnteredTitle -> {
                _state.value = _state.value.copy(
                    todo = _state.value.todo.copy(
                        title = event.value
                    )
                )
            }
            TodoNewUpdateEvent.SaveTodo -> {
                viewModelScope.launch (dispatcher + errorHandler){
                    try{
                        if(currentTodoId != null){
                            todoUseCases.updateTodoItem(_state.value.todo)
                        }else {
                            todoUseCases.addTodoItem(_state.value.todo.copy(
                                timestamp = System.currentTimeMillis(),
                                id = null
                            ))
                        }
                        _eventFlow.emit(UiEvent.SaveTodo)
                    }catch (e: Exception){
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: NewUpdateStrings.SAVE_ERROR
                            )
                        )
                    }
                }
            }
            TodoNewUpdateEvent.ToggleArchived -> {
                _state.value = _state.value.copy(
                    todo = _state.value.todo.copy(
                        archived = !_state.value.todo.archived
                    )
                )
            }
            TodoNewUpdateEvent.ToggleCompleted -> {
                _state.value = _state.value.copy(
                    todo = _state.value.todo.copy(
                        completed = !_state.value.todo.completed
                    )
                )
            }
        }
    }


}












