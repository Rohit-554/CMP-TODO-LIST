package io.jadu.todoApp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import io.jadu.todoApp.ui.theme.BodyXLarge
import io.jadu.todoApp.ui.theme.Spacing
import io.jadu.todoApp.ui.theme.TodoColors
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import todo_list.composeapp.generated.resources.Res
import todo_list.composeapp.generated.resources.arrow___left
import todo_list.composeapp.generated.resources.notification

@Composable
fun TodoTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "Today's Tasks",
    navController: NavController,
    actionImage : DrawableResource = Res.drawable.notification,
    onActionClick : (() -> Unit)? = null
) {
    val shouldShowNotificationDot = remember { mutableStateOf(false) }
    Row(
        modifier = modifier.fillMaxWidth().height(Spacing.s14).padding(horizontal = Spacing.s4),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .size(Spacing.s6)
                .bounceClickable { navController.navigateUp() },
            painter = painterResource(Res.drawable.arrow___left),
            contentDescription = ""
        )
        Text(
            text = title,
            style = BodyXLarge().copy(
                fontWeight = FontWeight.Black,
            )
        )
        Box(
            modifier = Modifier
                .size(Spacing.s6)
                .bounceClickable {
                    onActionClick?.invoke()
                }
        ) {
            Image(
                painter = painterResource(actionImage),
                contentDescription = null
            )

            if(shouldShowNotificationDot.value) Box(
                modifier = Modifier.offset(x = -Spacing.s1).size(Spacing.s2).background(
                    color = TodoColors.Primary.color,
                    shape = CircleShape
                ).align (Alignment.TopEnd),
            )
        }
    }
}