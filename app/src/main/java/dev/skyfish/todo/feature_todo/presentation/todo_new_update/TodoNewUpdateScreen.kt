package dev.skyfish.todo.feature_todo.presentation.todo_new_update

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.skyfish.todo.R
import dev.skyfish.todo.core.presentation.components.ArchiveButton
import dev.skyfish.todo.core.presentation.components.CompleteButton
import dev.skyfish.todo.core.presentation.components.DeleteButton
import dev.skyfish.todo.core.presentation.components.getTodoColors
import dev.skyfish.todo.core.util.ContentDescriptions
import dev.skyfish.todo.core.util.NewUpdateStrings
import dev.skyfish.todo.core.util.TodoListStrings
import dev.skyfish.todo.feature_todo.presentation.todo_new_update.components.HintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoNewUpdateScreen(
    navController: NavController,
    viewModel: TodoNewUpdateViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val todoColors = getTodoColors(todo = state.todo)

    val configuration = LocalConfiguration.current
    val isPortrait =
        configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    val topBarHeight = if(isPortrait) 64.dp else 0.dp
    val horizontalPadding = 16.dp
    val verticalPadding = if(isPortrait) 16.dp else 2.dp

    val backgroundImage = if (isPortrait) {
        R.drawable.background_portrait
    } else {
        R.drawable.background_landscape
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                TodoNewUpdateViewModel.UiEvent.Back -> {
                    navController.navigateUp()
                }

                TodoNewUpdateViewModel.UiEvent.SaveTodo -> {
                    navController.navigateUp()
                }

                is TodoNewUpdateViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            if (!isPortrait) {
                FloatingActionButton(
                    onClick = {
                        viewModel.onEvent(TodoNewUpdateEvent.SaveTodo)
                    },
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = ContentDescriptions.SAVE_TODO,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        topBar = {
            if (isPortrait) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = TodoListStrings.TODO_LIST,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.headlineMedium,
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
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                viewModel.onEvent(TodoNewUpdateEvent.Back)
                            },
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = ContentDescriptions.BACK,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    actions = {},
                    scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
                )

            }
        },
        bottomBar = {
            if (isPortrait) {
                BottomAppBar(
                    actions = {
                        CompleteButton(
                            onCompleteClick = {
                                viewModel.onEvent(TodoNewUpdateEvent.ToggleCompleted)
                            },
                            color = todoColors.checkColor,
                            completed = state.todo.completed
                        )
                        ArchiveButton(
                            onArchiveClick = {
                                viewModel.onEvent(TodoNewUpdateEvent.ToggleArchived)
                            }
                        )
                        DeleteButton(
                            onDeleteClick = {
                                scope.launch {
                                    val confirm = snackbarHostState.showSnackbar(
                                        message = NewUpdateStrings.CONFIRM_DELETE,
                                        actionLabel = NewUpdateStrings.YES
                                    )
                                    if (confirm == SnackbarResult.ActionPerformed) {
                                        viewModel.onEvent(TodoNewUpdateEvent.Delete)
                                    }
                                }
                            }
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                viewModel.onEvent(TodoNewUpdateEvent.SaveTodo)
                            },
                            shape = CircleShape,
                            containerColor = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(
                                imageVector = Icons.Default.Save,
                                contentDescription = ContentDescriptions.SAVE_TODO,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.5f)
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)}
    ) { padding ->

        Box(
            contentAlignment = Alignment.TopStart,
            modifier = Modifier
                .fillMaxSize()
                .background(color = todoColors.backgroundColor)
        ) {
            Image(
                painter = painterResource(id = backgroundImage),
                contentDescription = ContentDescriptions.BACKGROUND_IMAGE,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.TopStart
            )
            Column(
                modifier = Modifier
                    .padding(top = topBarHeight)
                    .fillMaxSize()
            ){
                HintTextField(
                    text = state.todo.title,
                    hint = NewUpdateStrings.TITLE_HINT,
                    textColor = todoColors.textColor,
                    onValueChange = {
                         viewModel.onEvent(TodoNewUpdateEvent.EnteredTitle(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(TodoNewUpdateEvent.ChangedTitleFocus(it))
                    },
                    isHintVisible = state.isTitleHintVisible,
                    singleLine = true,
                    fontSize = 40.sp,
                    modifier = Modifier.padding(
                        horizontal = horizontalPadding,
                        vertical = verticalPadding
                    )
                )
                Spacer(modifier = Modifier.height(verticalPadding))
                HintTextField(
                    text = state.todo.description,
                    hint = NewUpdateStrings.DESCRIPTION_HINT,
                    textColor = todoColors.textColor,
                    onValueChange = {
                        viewModel.onEvent(TodoNewUpdateEvent.EnteredDescription(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(TodoNewUpdateEvent.ChangedDescriptionFocus(it))
                    },
                    isHintVisible = state.isDescriptionHintVisible,
                    singleLine = false,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(
                            horizontal = horizontalPadding,
                            vertical = verticalPadding
                    )
                )
            }
        }

    }
}









