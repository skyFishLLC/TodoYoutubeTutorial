package dev.skyfish.todo.feature_todo.domain.util

sealed class TodoItemOrder(
    val sortingDirection: SortingDirection,
    val showArchived: Boolean
){
    class Title(sortingDirection: SortingDirection, showArchived: Boolean): TodoItemOrder(sortingDirection, showArchived)
    class Time(sortingDirection: SortingDirection, showArchived: Boolean): TodoItemOrder(sortingDirection, showArchived)
    class Completed(sortingDirection: SortingDirection, showArchived: Boolean): TodoItemOrder(sortingDirection, showArchived)

    fun copy(sortingDirection: SortingDirection, showArchived: Boolean): TodoItemOrder {
        return when(this){
            is Title -> Title(sortingDirection, showArchived)
            is Time -> Title(sortingDirection, showArchived)
            is Completed -> Title(sortingDirection, showArchived)
        }
    }
}
