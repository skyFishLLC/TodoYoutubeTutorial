package dev.skyfish.todo.feature_todo.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.skyfish.todo.feature_todo.data.local.TodoDao
import dev.skyfish.todo.feature_todo.data.local.TodoDatabase
import dev.skyfish.todo.feature_todo.data.remote.TodoApi
import dev.skyfish.todo.feature_todo.domain.repo.TodoListRepo
import dev.skyfish.todo.feature_todo.domain.use_case.AddTodoItemUseCase
import dev.skyfish.todo.feature_todo.domain.use_case.DeleteTodoItemUseCase
import dev.skyfish.todo.feature_todo.domain.use_case.GetSortedTodoItemsUseCase
import dev.skyfish.todo.feature_todo.domain.use_case.GetTodoItemFromRemoteUseCase
import dev.skyfish.todo.feature_todo.domain.use_case.GetTodoItemsFromCacheArchiveFilteredUseCase
import dev.skyfish.todo.feature_todo.domain.use_case.GetTodoItemsFromCacheUseCase
import dev.skyfish.todo.feature_todo.domain.use_case.GetTodoItemsFromRemoteArchiveFilteredUseCase
import dev.skyfish.todo.feature_todo.domain.use_case.TodoUseCases
import dev.skyfish.todo.feature_todo.domain.use_case.ToggleArchivedTodoItemUseCase
import dev.skyfish.todo.feature_todo.domain.use_case.ToggleCompletedTodoItemUseCase
import dev.skyfish.todo.feature_todo.domain.use_case.UpdateTodoItemUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoModule {

    @Provides
    @Singleton
    fun providesTodoUseCases(repo: TodoListRepo): TodoUseCases{
        val getTodoItemsFromCacheUseCase = GetTodoItemsFromCacheUseCase(repo)
        val getSortedTodoItemsUseCase = GetSortedTodoItemsUseCase(repo)
        val getTodoItemsFromRemoteArchiveFilteredUseCase = GetTodoItemsFromRemoteArchiveFilteredUseCase(
            getSortedTodoItemsUseCase = getSortedTodoItemsUseCase
        )
        return TodoUseCases(
            getSortedTodoItemsUseCase = getSortedTodoItemsUseCase,
            addTodoItemUseCase = AddTodoItemUseCase(repo),
            deleteTodoItemUseCase = DeleteTodoItemUseCase(repo),
            toggleCompletedTodoItemUseCase = ToggleCompletedTodoItemUseCase(repo),
            toggleArchivedTodoItemUseCase = ToggleArchivedTodoItemUseCase(repo),
            getTodoItemsFromCacheUseCase = GetTodoItemsFromCacheUseCase(repo),
            getTodoItemFromRemoteUseCase = GetTodoItemFromRemoteUseCase(repo),
            updateTodoItemUseCase = UpdateTodoItemUseCase(repo),
            getTodoItemsFromCacheArchiveFilteredUseCase = GetTodoItemsFromCacheArchiveFilteredUseCase(
                getTodoItemsFromCacheUseCase = getTodoItemsFromCacheUseCase
            ),
            getTodoItemsFromRemoteArchiveFilteredUseCase = getTodoItemsFromRemoteArchiveFilteredUseCase
        )
    }

    @Provides
    fun providesRetrofitApi(retrofit: Retrofit): TodoApi {
        return retrofit.create(TodoApi::class.java)
    }

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl("https://todo-a6632-default-rtdb.firebaseio.com/")
            .build()
    }

    @Provides
    fun providesRoomDao(database: TodoDatabase): TodoDao {
        return database.dao
    }

    @Singleton
    @Provides
    fun providesRoomDb(
        @ApplicationContext appContext: Context
    ): TodoDatabase{
        return Room.databaseBuilder(
            appContext.applicationContext,
            TodoDatabase::class.java,
            "todo_database"
        ).fallbackToDestructiveMigration().build()
    }
}