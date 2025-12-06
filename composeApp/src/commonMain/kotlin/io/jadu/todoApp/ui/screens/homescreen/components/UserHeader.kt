package io.jadu.todoApp.ui.screens.homescreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import io.jadu.todoApp.data.model.UserProfile
import io.jadu.todoApp.ui.theme.BodyLarge
import io.jadu.todoApp.ui.theme.BodySmall
import io.jadu.todoApp.ui.theme.BodyXXLarge
import io.jadu.todoApp.ui.theme.Spacing
import io.jadu.todoApp.ui.theme.TodoColors
import io.jadu.todoApp.ui.uiutils.HSpacer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import todo_list.composeapp.generated.resources.Res
import todo_list.composeapp.generated.resources.notification
import todo_list.composeapp.generated.resources.user_octagon

@Composable
@Preview(showBackground = true)
fun UserHeader(
    userProfile: UserProfile = UserProfile()
) {

    val shouldShowNotificationDot = remember { mutableStateOf(true) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(Spacing.s14)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (userProfile.photoData != null) {
                    AsyncImage(
                        model = userProfile.photoData,
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(Spacing.s14)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else if (userProfile.name.isNotEmpty()) {
                    Text(
                        userProfile.name.first().uppercase(),
                        style = BodyXXLarge().copy(
                            color = TodoColors.Light.color
                        )
                    )
                } else {
                    Image(
                        painter = painterResource(Res.drawable.user_octagon),
                        contentDescription = "Default Profile Picture",
                        colorFilter = ColorFilter.tint(
                            color = TodoColors.Primary.color
                        ),
                        modifier = Modifier
                            .size(Spacing.s14),
                    )
                }
            }

            HSpacer()
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Hello!",
                    style = BodySmall()
                )
                Text(
                    text = if (userProfile.name.isNotEmpty()) userProfile.name else "User",
                    style = BodyLarge()
                )
            }

        }

        Box(
            modifier = Modifier.size(Spacing.s6)
        ) {
            Image(
                painter = painterResource(Res.drawable.notification),
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