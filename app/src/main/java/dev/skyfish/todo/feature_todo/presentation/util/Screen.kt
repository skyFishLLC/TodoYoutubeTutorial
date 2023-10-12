package dev.skyfish.todo.feature_todo.presentation.util

sealed class Screen(val route: String){
    object TodoItemListScreen: Screen("todoItemList_screen")
    object TodoNewUpdateScreen: Screen("todoNewUpdate_screen")
}
