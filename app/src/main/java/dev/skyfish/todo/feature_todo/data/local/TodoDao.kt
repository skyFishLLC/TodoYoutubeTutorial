package dev.skyfish.todo.feature_todo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.skyfish.todo.feature_todo.data.local.dto.LocalTodoItem

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAllTodoItems(): List<LocalTodoItem>

    @Query("SELECT *  FROM todo WHERE id = :id")
    suspend fun getSingleTodoItemById(id: Int): LocalTodoItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllTodoItems(todos: List<LocalTodoItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodoItem(todo: LocalTodoItem): Long

    @Delete
    suspend fun deleteTodoItem(todo: LocalTodoItem)
}