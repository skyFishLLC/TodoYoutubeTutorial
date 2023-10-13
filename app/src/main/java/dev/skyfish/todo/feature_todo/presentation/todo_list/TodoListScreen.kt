package dev.skyfish.todo.feature_todo.presentation.todo_list

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.skyfish.todo.R
import dev.skyfish.todo.core.util.ContentDescriptions
import dev.skyfish.todo.core.util.TodoListStrings
import dev.skyfish.todo.feature_todo.presentation.todo_list.components.SortingDrawerOptions
import dev.skyfish.todo.feature_todo.presentation.todo_list.components.TodoItemCard
import dev.skyfish.todo.feature_todo.presentation.util.Screen
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    navController: NavController,
    viewModel: TodoListViewModel = hiltViewModel()
){
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    val backgroundImage = if(isPortrait){
        R.drawable.background_portrait
    }else{
        R.drawable.background_landscape
    }

    LaunchedEffect(key1 = true){
        viewModel.getTodoItems()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Box(modifier = Modifier.fillMaxWidth(0.65f)){
                ModalDrawerSheet {
                    Text(
                        text = TodoListStrings.SORT_BY,
                        modifier = Modifier.padding(16.dp),
                        fontSize = 34.sp,
                        lineHeight = 38.sp
                    )
                    Divider()
                    SortingDrawerOptions(
                        todoItemOrder = state.todoItemOrder,
                        onOrderChange = { order ->
                            viewModel.onEvent(TodoListEvent.Sort(order))
                        })
                }
            }
        }
    ) {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    navController.navigate(Screen.TodoNewUpdateScreen.route)
                },
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = ContentDescriptions.ADD_TODO,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = TodoListStrings.TODO_LIST,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                        scrolledContainerColor = MaterialTheme.colorScheme.primary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    navigationIcon = {},
                    actions = {
                        IconButton(
                            onClick = {
                                scope.launch { drawerState.open() }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = ContentDescriptions.SORTING_MENU
                            )
                        }
                    },
                    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                )
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState)}
        ) { padding ->
            Box(
                contentAlignment = Alignment.TopStart,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
            ){
                Image(
                    painter = painterResource(id = backgroundImage),
                    contentDescription = ContentDescriptions.BACKGROUND_IMAGE,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize(),
                    alignment = Alignment.TopStart
                )
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp)
                            .padding(top = 64.dp)
                    ){
                        items(state.todoItems){todo ->
                            TodoItemCard(
                                todo = todo,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                onDeleteClick = {
                                     viewModel.onEvent(TodoListEvent.Delete(todo))
                                    scope.launch {
                                        val undo = snackbarHostState.showSnackbar(
                                            message = TodoListStrings.TODO_ITEM_DELETED,
                                            actionLabel = TodoListStrings.UNDO
                                        )
                                        if(undo == SnackbarResult.ActionPerformed){
                                            viewModel.onEvent(TodoListEvent.UndoDelete)
                                        }
                                    }
                                },
                                onCompleteClick = {
                                         viewModel.onEvent(TodoListEvent.ToggleCompleted(todo))
                                },
                                onArchiveClick = {
                                    viewModel.onEvent(TodoListEvent.ToggleArchived(todo))
                                },
                                onCardClick = {
                                    navController.navigate(
                                        Screen.TodoNewUpdateScreen.route + "?todoId=${todo.id}"
                                    )
                                }
                            )
                        }
                    }
                }
                if(state.isLoading){
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            Modifier.semantics {
                                this.contentDescription = ContentDescriptions.LOADING_INDICATOR
                            }
                        )
                    }
                }
                if(state.error != null){
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                       Text(
                           text = state.error,
                           fontSize = 30.sp,
                           lineHeight = 36.sp
                       )
                    }
                }
            }
        }
    }


}







