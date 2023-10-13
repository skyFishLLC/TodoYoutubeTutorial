package dev.skyfish.todo.feature_todo.presentation.todo_new_update

import androidx.compose.ui.focus.FocusState

sealed class TodoNewUpdateEvent {
    data class EnteredTitle(val value: String) : TodoNewUpdateEvent()
    data class ChangedTitleFocus(val focusState: FocusState) : TodoNewUpdateEvent()
    data class EnteredDescription(val value: String) : TodoNewUpdateEvent()
    data class ChangedDescriptionFocus(val focusState: FocusState) : TodoNewUpdateEvent()
    object Delete: TodoNewUpdateEvent()
    object ToggleCompleted: TodoNewUpdateEvent()
    object ToggleArchived: TodoNewUpdateEvent()
    object SaveTodo: TodoNewUpdateEvent()
    object Back: TodoNewUpdateEvent()
}
