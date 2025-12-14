package io.jadu.todoApp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import io.github.ismoy.imagepickerkmp.domain.extensions.loadBytes
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import io.github.ismoy.imagepickerkmp.presentation.ui.components.GalleryPickerLauncher
import io.jadu.todoApp.ui.components.TodoElevatedCard
import io.jadu.todoApp.ui.components.TodoTextField
import io.jadu.todoApp.ui.components.TodoTopAppBar
import io.jadu.todoApp.ui.components.bounceClickable
import io.jadu.todoApp.ui.route.NavRoute
import io.jadu.todoApp.ui.screens.homescreen.components.showSnackBar
import io.jadu.todoApp.ui.theme.BodyLarge
import io.jadu.todoApp.ui.theme.BodyNormal
import io.jadu.todoApp.ui.theme.BodySmall
import io.jadu.todoApp.ui.theme.BodyXLarge
import io.jadu.todoApp.ui.theme.H1TextStyle
import io.jadu.todoApp.ui.theme.H2TextStyle
import io.jadu.todoApp.ui.theme.Spacing
import io.jadu.todoApp.ui.theme.TodoColors
import io.jadu.todoApp.ui.uiutils.VSpacer
import io.jadu.todoApp.ui.utils.UiEvent
import io.jadu.todoApp.ui.viewModel.SettingsViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import todo_list.composeapp.generated.resources.Res
import todo_list.composeapp.generated.resources.user_octagon


@Composable
@Preview
fun SettingsPage(
    navHostController: NavHostController,
    viewModel: SettingsViewModel = koinInject()
) {
    val uiState by viewModel.uiState.collectAsState()

    var name by remember { mutableStateOf("") }
    var isEditing by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    var showDialog by remember { mutableStateOf(false) }
    val isFeedback = remember { mutableStateOf(false) }

    // Image picker state
    var showGallery by remember { mutableStateOf(false) }
    var selectedPhoto by remember { mutableStateOf<GalleryPhotoResult?>(null) }

    // Sync name from database
    LaunchedEffect(uiState.userProfile.name) {
        if (name.isEmpty() && uiState.userProfile.name.isNotEmpty()) {
            name = uiState.userProfile.name
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvents.collect { event ->
            when(event) {
                is UiEvent.OnSuccess -> showSnackBar(event.message)
                is UiEvent.ShowError -> showSnackBar(event.message, positiveMessage = false)
            }
        }
    }

    // Gallery picker
    if (showGallery) {
        GalleryPickerLauncher(
            onPhotosSelected = { photos ->
                selectedPhoto = photos.firstOrNull()
                selectedPhoto?.let {
                    // GalleryPhotoResult contains the image bytes in 'bytes' property
                    viewModel.updateUserPhoto(it.loadBytes())
                }
                showGallery = false
            },
            onError = { showGallery = false },
            onDismiss = { showGallery = false },
            allowMultiple = false
        )
    }

    LaunchedEffect(isEditing) {
        if (isEditing) {
            focusRequester.requestFocus()
        }
    }

    Scaffold(
        topBar = {
            TodoTopAppBar(
                modifier = Modifier.systemBarsPadding(),
                title = "Settings",
                navController = navHostController
            )
        }
    ) { padding ->
        TodoBackgroundScreen {
            BoxWithConstraints(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            if (isEditing) {
                                isEditing = false
                            }
                        })
                    }
            ) {
                val screenHeight = maxHeight
                val cardHeight = screenHeight * 0.15f

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Area A (30%) - Profile Section
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.3f),
                        contentAlignment = Alignment.Center,
                    ) {
                        // Edit/Save Icon
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .padding(top = 8.dp, end = 8.dp)
                                .align(Alignment.TopEnd)
                        ) {
                            Icon(
                                imageVector = if (isEditing) Icons.Default.Check else Icons.Default.Edit,
                                contentDescription = if (isEditing) "Save Name" else "Edit Name",
                                tint = if (isEditing) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .size(if (isEditing) 36.dp else 24.dp)
                                    .align(Alignment.Center)
                                    .bounceClickable {
                                        if (isEditing) {
                                            if (name.isEmpty()) {
                                                // show a message to user
                                                return@bounceClickable
                                            }
                                            viewModel.updateUserName(name)
                                        }
                                        isEditing = !isEditing
                                    }
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            // Profile Image
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                                    .clickable(enabled = isEditing) {
                                        showGallery = true
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                val photoToDisplay = selectedPhoto?.loadBytes() ?: uiState.userProfile.photoData

                                if (photoToDisplay != null) {
                                    AsyncImage(
                                        model = photoToDisplay,
                                        contentDescription = "Profile Picture",
                                        modifier = Modifier
                                            .size(100.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(Res.drawable.user_octagon),
                                        contentDescription = "Profile Picture",
                                        colorFilter = ColorFilter.tint(
                                            color = TodoColors.Primary.color
                                        ),
                                        modifier = Modifier
                                            .size(100.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }

                            VSpacer(Spacing.s4)

                            // Name Section
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                if (isEditing) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        TextField(
                                            modifier = Modifier.focusRequester(focusRequester),
                                            value = name,
                                            onValueChange = {
                                                if (it.length <= 13) {
                                                    name = it
                                                }
                                            },
                                            label = null,
                                            colors = TextFieldDefaults.colors(
                                                focusedContainerColor = Color.Transparent,
                                                unfocusedContainerColor = Color.Transparent,
                                                focusedIndicatorColor = Color.Transparent,
                                                unfocusedIndicatorColor = Color.Transparent,
                                                cursorColor = MaterialTheme.colorScheme.onSurface,
                                                selectionColors = TextSelectionColors(
                                                    handleColor = MaterialTheme.colorScheme.outline,
                                                    backgroundColor = MaterialTheme.colorScheme.outline
                                                )
                                            ),
                                            textStyle = H2TextStyle().copy(
                                                fontWeight = FontWeight.Bold
                                            ),
                                            placeholder = {
                                                Text(
                                                    text = "Add Your Name",
                                                    style = H1TextStyle().copy(
                                                        fontSize = 36.sp,
                                                        fontStyle = FontStyle.Italic,
                                                        color = Color.Gray
                                                    ),
                                                )
                                            }
                                        )
                                        Spacer(modifier = Modifier.width(Spacing.s2))
                                    }
                                } else {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        if (name.isEmpty()) {
                                            Text(
                                                text = "Edit Your Name and Photo",
                                                style = BodyXLarge().copy(
                                                    fontWeight = FontWeight.Bold
                                                ),
                                            )
                                        } else {
                                            Text(
                                                text = name,
                                                style = H1TextStyle().copy(
                                                    fontSize = 36.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.Gray
                                                ),
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(Spacing.s3))
                                    }
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Outlined.Edit,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }

                    // Area B (70%) - Content Section
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.7f)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            TodoElevatedCard(
                                modifier = Modifier.padding(horizontal = Spacing.s4)
                            ) {
                                // Stats Card
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(cardHeight)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                    ) {
                                        // Notes Count
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .fillMaxHeight(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            InfoCard(
                                                title = "${uiState.completedTodos}",
                                                subtitle = "Completed",
                                                modifier = Modifier
                                            )
                                        }

                                        // Divider
                                        Box(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .background(
                                                    color = MaterialTheme.colorScheme.onBackground.copy(
                                                        alpha = 0.3f
                                                    )
                                                )
                                                .width(1.dp)
                                        )

                                        // TODOs Count
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .fillMaxHeight(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            InfoCard(
                                                title = "${uiState.incompleteTodos}",
                                                subtitle = "Remaining",
                                                modifier = Modifier
                                            )
                                        }
                                    }

                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Action Buttons
                            BorderButton(
                                "About Us",
                                onClick = {
                                    navHostController.navigate(NavRoute.AboutUs)
                                },
                                subtitle = "Know more about us"
                            )
                            BorderButton(
                                "FeedBack",
                                onClick = {
                                    showDialog = true
                                    isFeedback.value = true
                                },
                                subtitle = "Give us your valuable feedback"
                            )
                            BorderButton(
                                "Report A Bug",
                                onClick = {
                                    showDialog = true
                                    isFeedback.value = false
                                },
                                subtitle = "Report any bugs you find"
                            )
                        }
                    }
                }
            }
        }


        if (showDialog) {
            TextFieldDialogue(
                onDismissRequest = {
                    showDialog = false
                },
                onSubmit = { bugDescription ->
                    // Handle submission
                },
                isFeedbackClicked = isFeedback.value
            )
        }
    }
}

@Composable
private fun BorderButton(
    title: String,
    onClick: () -> Unit,
    subtitle: String
) {
    TodoElevatedCard(
        modifier = Modifier.padding(horizontal = Spacing.s4),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = title,
                    style = BodyLarge().copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = BodySmall(),
                    color = Color.Gray
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.NavigateNext,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun InfoCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = H2TextStyle(),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = subtitle,
            style = BodyNormal(),
            color = Color.Gray
        )
    }
}

@Composable
private fun TextFieldDialogue(
    onDismissRequest: () -> Unit,
    onSubmit: (String) -> Unit,
    isFeedbackClicked: Boolean
) {
    var text by remember { mutableStateOf("") }

    AlertDialog(
        containerColor = TodoColors.Light.color,
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                if (isFeedbackClicked) "Feedback" else "Report a Bug",
                style = BodyLarge()
            )
        },
        text = {
            TodoTextField(
                value = text,
                onValueChange = { text = it },
                placeholder = if (isFeedbackClicked) "Share your feedback..." else "Describe the bug...",
                modifier = Modifier.fillMaxWidth(),
                focusedBorderColor = MaterialTheme.colorScheme.outline,
                singleLine = false
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSubmit(text)
                    onDismissRequest()
                }
            ) {
                Text(
                    "Submit",
                    style = BodyNormal()
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(
                    "Cancel",
                    style = BodyNormal()
                )
            }
        }
    )
}

