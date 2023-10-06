package dev.skyfish.todo.feature_todo.domain.util

sealed class TodoItemOrder(
    val orderDirection: OrderDirection
){
    class Title(orderDirection: OrderDirection): TodoItemOrder(orderDirection)
    class Time(orderDirection: OrderDirection): TodoItemOrder(orderDirection)
    class Completed(orderDirection: OrderDirection): TodoItemOrder(orderDirection)

    fun copy(orderDirection: OrderDirection): TodoItemOrder{
        return when(this){
            is Title -> Title(orderDirection)
            is Time -> Time(orderDirection)
            is Completed -> Completed(orderDirection)
        }
    }
}
