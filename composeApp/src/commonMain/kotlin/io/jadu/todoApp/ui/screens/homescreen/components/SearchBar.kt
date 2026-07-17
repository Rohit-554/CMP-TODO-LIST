package io.jadu.todoApp.ui.screens.homescreen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import io.jadu.todoApp.data.model.TaskStatus
import io.jadu.todoApp.data.model.TodoItem
import io.jadu.todoApp.ui.components.bounceClickable
import io.jadu.todoApp.ui.theme.BodyNormal
import io.jadu.todoApp.ui.theme.BodySmall
import io.jadu.todoApp.ui.theme.BodyXSmall
import io.jadu.todoApp.ui.theme.LocalDarkTheme
import io.jadu.todoApp.ui.theme.Spacing
import io.jadu.todoApp.ui.theme.TodoColors
import io.jadu.todoApp.ui.uiutils.VSpacer
import org.jetbrains.compose.resources.stringResource
import todo_list.composeapp.generated.resources.Res
import todo_list.composeapp.generated.resources.search_hint

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val isDark = LocalDarkTheme.current
    var isFocused by remember { mutableStateOf(false) }

    val containerColor = if (isDark) MaterialTheme.colorScheme.surfaceVariant
                         else TodoColors.LightPrimary.color

    val unfocusedBorderColor = if (isDark)
        TodoColors.DarkOutline.color.copy(alpha = 0.7f)
    else
        TodoColors.Primary.color.copy(alpha = 0.2f)

    val iconTint by animateColorAsState(
        targetValue = if (isFocused || query.isNotEmpty()) MaterialTheme.colorScheme.primary
                      else MaterialTheme.colorScheme.secondary,
        animationSpec = tween(200),
        label = "searchIconTint"
    )

    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (isFocused) 6.dp else 2.dp,
                shape = RoundedCornerShape(Spacing.s9),
                ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
            )
            .onFocusChanged { isFocused = it.isFocused },
        placeholder = {
            Text(
                text = stringResource(Res.string.search_hint),
                style = BodyNormal().copy(
                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = iconTint
            )
        },
        trailingIcon = {
            AnimatedVisibility(visible = query.isNotEmpty()) {
                IconButton(onClick = {
                    onClear()
                    focusManager.clearFocus()
                }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(Spacing.s9),
        textStyle = BodyNormal().copy(color = MaterialTheme.colorScheme.onSurface),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = unfocusedBorderColor,
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        )
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchResultItem(
    todo: TodoItem,
    searchQuery: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = LocalDarkTheme.current

    val statusColor = when (todo.status) {
        TaskStatus.DONE -> TodoColors.Emerald.color
        TaskStatus.IN_PROGRESS -> TodoColors.Orange.color
        TaskStatus.TO_DO -> MaterialTheme.colorScheme.primary
    }

    val cardBackground = MaterialTheme.colorScheme.surface
    val highlightColor = if (isDark) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                         else TodoColors.LightPrimary.color
    val tagBackground = if (isDark) MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
                        else TodoColors.LightPrimary.color
    val tagHighlight = if (isDark) MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
                       else Color(0xFFD5C8FF)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .bounceClickable(pressedScale = 0.97f, onClick = onClick)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(Spacing.s4),
                ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.04f),
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
            )
            .background(color = cardBackground, shape = RoundedCornerShape(Spacing.s4))
            .padding(Spacing.s4)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .background(
                        color = statusColor.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(Spacing.s2)
                    )
                    .padding(horizontal = Spacing.s2, vertical = Spacing.sHalf)
            ) {
                Text(
                    text = todo.status.name.replace('_', ' '),
                    style = BodyXSmall().copy(
                        color = statusColor,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            VSpacer(Spacing.s2)

            Text(
                text = highlightText(todo.title, searchQuery, highlightColor),
                style = BodyNormal().copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            if (todo.description.isNotBlank()) {
                VSpacer(Spacing.s1)
                Text(
                    text = highlightText(todo.description, searchQuery, highlightColor),
                    style = BodySmall().copy(color = MaterialTheme.colorScheme.secondary),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            if (todo.tags.isNotEmpty()) {
                VSpacer(Spacing.s2)
                FlowRow(horizontalArrangement = Arrangement.spacedBy(Spacing.s1)) {
                    todo.tags.forEach { tag ->
                        Box(
                            modifier = Modifier
                                .background(color = tagBackground, shape = RoundedCornerShape(Spacing.s2))
                                .padding(horizontal = Spacing.s2, vertical = Spacing.sHalf)
                        ) {
                            Text(
                                text = highlightText("#$tag", searchQuery, tagHighlight),
                                style = BodyXSmall().copy(color = MaterialTheme.colorScheme.primary)
                            )
                        }
                    }
                }
            }
        }
    }
}

fun highlightText(text: String, query: String, highlightColor: Color): AnnotatedString {
    if (query.isBlank()) return buildAnnotatedString { append(text) }
    val trimmedQuery = query.trim()
    return buildAnnotatedString {
        val lowerText = text.lowercase()
        val lowerQuery = trimmedQuery.lowercase()
        var cursor = 0
        while (cursor < text.length) {
            val matchIndex = lowerText.indexOf(lowerQuery, cursor)
            if (matchIndex == -1) {
                append(text.substring(cursor))
                break
            }
            append(text.substring(cursor, matchIndex))
            withStyle(SpanStyle(background = highlightColor, fontWeight = FontWeight.Bold)) {
                append(text.substring(matchIndex, matchIndex + trimmedQuery.length))
            }
            cursor = matchIndex + trimmedQuery.length
        }
    }
}
