package dev.skyfish.todo.feature_todo.domain.util

sealed class TodoItemOrder(
    var sortingDirection: SortingDirection,
    val showArchived: Boolean
){
    class Title(sortingDirection: SortingDirection, showArchived: Boolean): TodoItemOrder(sortingDirection, showArchived)
    class Time(sortingDirection: SortingDirection, showArchived: Boolean): TodoItemOrder(sortingDirection, showArchived)
    class Completed(sortingDirection: SortingDirection, showArchived: Boolean): TodoItemOrder(sortingDirection, showArchived)

    fun copy(sortingDirection: SortingDirection, showArchived: Boolean): TodoItemOrder {
        return when(this){
            is Title -> Title(sortingDirection, showArchived)
            is Time -> Time(sortingDirection, showArchived)
            is Completed -> Completed(sortingDirection, showArchived)
        }
    }
}
