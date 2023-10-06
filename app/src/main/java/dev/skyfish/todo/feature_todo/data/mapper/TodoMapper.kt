package dev.skyfish.todo.feature_todo.data.mapper

import dev.skyfish.todo.feature_todo.data.local.dto.LocalTodoItem
import dev.skyfish.todo.feature_todo.data.remote.dto.RemoteTodoItem
import dev.skyfish.todo.feature_todo.domain.model.TodoItem

fun TodoItem.toLocalTodoItem(): LocalTodoItem{
    return LocalTodoItem(
        title = title,
        description = description,
        timestamp = timestamp,
        completed = completed,
        archived = archived,
        id = id
    )
}

fun TodoItem.toRemoteTodoItem(): RemoteTodoItem {
    return RemoteTodoItem(
        title = title,
        description = description,
        timestamp = timestamp,
        completed = completed,
        archived = archived,
        id = id
    )
}

fun LocalTodoItem.toTodoItem(): TodoItem{
    return TodoItem(
        title = title,
        description = description,
        timestamp = timestamp,
        completed = completed,
        archived = archived,
        id = id
    )
}

fun LocalTodoItem.toRemoteTodoItem(): RemoteTodoItem{
    return RemoteTodoItem(
        title = title,
        description = description,
        timestamp = timestamp,
        completed = completed,
        archived = archived,
        id = id
    )
}

fun RemoteTodoItem.toTodoItem(): TodoItem{
    return TodoItem(
        title = title,
        description = description,
        timestamp = timestamp,
        completed = completed,
        archived = archived,
        id = id
    )
}

fun RemoteTodoItem.toLocalTodoItem(): LocalTodoItem{
    return LocalTodoItem(
        title = title,
        description = description,
        timestamp = timestamp,
        completed = completed,
        archived = archived,
        id = id
    )
}

fun List<TodoItem>.toLocalTodoItemList(): List<LocalTodoItem>{
    return this.map {todo ->
        LocalTodoItem(
            title = todo.title,
            description = todo.description,
            timestamp = todo.timestamp,
            completed = todo.completed,
            archived = todo.archived,
            id = todo.id
        )
    }
}

fun List<TodoItem>.toRemoteTodoItemList(): List<RemoteTodoItem>{
    return this.map {todo ->
        RemoteTodoItem(
            title = todo.title,
            description = todo.description,
            timestamp = todo.timestamp,
            completed = todo.completed,
            archived = todo.archived,
            id = todo.id
        )
    }
}

fun List<LocalTodoItem>.toTodoItemListFromLocal(): List<TodoItem>{
    return this.map {todo ->
        TodoItem(
            title = todo.title,
            description = todo.description,
            timestamp = todo.timestamp,
            completed = todo.completed,
            archived = todo.archived,
            id = todo.id
        )
    }
}

fun List<LocalTodoItem>.toRemoteTodoItemListFromLocal(): List<RemoteTodoItem>{
    return this.map {todo ->
        RemoteTodoItem(
            title = todo.title,
            description = todo.description,
            timestamp = todo.timestamp,
            completed = todo.completed,
            archived = todo.archived,
            id = todo.id
        )
    }
}

fun List<RemoteTodoItem>.toTodoItemListFromRemote(): List<TodoItem>{
    return this.map {todo ->
        TodoItem(
            title = todo.title,
            description = todo.description,
            timestamp = todo.timestamp,
            completed = todo.completed,
            archived = todo.archived,
            id = todo.id
        )
    }
}

fun List<RemoteTodoItem>.toLocalTodoItemListFromRemote(): List<LocalTodoItem>{
    return this.map {todo ->
        LocalTodoItem(
            title = todo.title,
            description = todo.description,
            timestamp = todo.timestamp,
            completed = todo.completed,
            archived = todo.archived,
            id = todo.id
        )
    }
}



