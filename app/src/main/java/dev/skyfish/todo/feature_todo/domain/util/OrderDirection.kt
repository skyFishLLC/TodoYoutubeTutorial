package dev.skyfish.todo.feature_todo.domain.util

import androidx.room.Index

sealed class OrderDirection{
    object Up: OrderDirection()
    object Down: OrderDirection()
}
