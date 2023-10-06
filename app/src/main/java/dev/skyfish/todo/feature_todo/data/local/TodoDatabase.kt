package dev.skyfish.todo.feature_todo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.skyfish.todo.feature_todo.data.local.dto.LocalTodoItem

@Database(
    entities = [LocalTodoItem::class],
    version = 1,
    exportSchema = false
)
abstract class TodoDatabase: RoomDatabase() {
    abstract val dao: TodoDao

    companion object{
        const val DATABASE_NAME = "todo_db"
    }
}