package io.jadu.todoApp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.jadu.todoApp.ui.components.CurvedButton
import io.jadu.todoApp.ui.theme.TodoColors
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import todo_list.composeapp.generated.resources.Res
import todo_list.composeapp.generated.resources.blue_desk_calendar
import todo_list.composeapp.generated.resources.blue_stopwatch_with_pink_arrow
import todo_list.composeapp.generated.resources.female_sitting_on_the_floor_with_cup_in_hand_and_laptop_on_leg
import todo_list.composeapp.generated.resources.multicolored_smartphone_notifications
import todo_list.composeapp.generated.resources.pie_chart
import todo_list.composeapp.generated.resources.vase_with_tulips__glasses_and_pencil

@Composable
@Preview
fun OnboardingScreen() {
    TodoBackgroundScreen(
        shouldShowDotsAndIcons = true
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.female_sitting_on_the_floor_with_cup_in_hand_and_laptop_on_leg),
                    contentDescription = "Task management illustration",
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Fit
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 40.dp)
            ) {
                Text(
                    text = "Task Management &\nTo-Do List",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 36.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "This productive tool is designed to help\nyou better manage your task\nproject-wisely conveniently!",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF6B6B6B),
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                // CTA Button
                CurvedButton(
                    onClick = {},
                    text = "Let's Start",
                    modifier = Modifier.fillMaxWidth()
                )
            }


        }
    }
}

@Composable
fun TodoBackgroundScreen(
    modifier : Modifier = Modifier,
    shouldShowDotsAndIcons: Boolean = false,
    content: @Composable () -> Unit = {},
) {
    BoxWithConstraints (
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        TodoColors.Cream.color,
                        TodoColors.SoftCyan.color,
                        TodoColors.LightBlue.color
                    ),
                    center = Offset(0.5f,0.3f),
                    radius = 1500f
                )
            )
    ) {
        FloatingElements(shouldShowDotsAndIcons = shouldShowDotsAndIcons)
        content()
    }
}

@Composable
fun BoxWithConstraintsScope.FloatingElements(
    shouldShowDotsAndIcons : Boolean = true
) {
    if(shouldShowDotsAndIcons) {
        Image(
            painter = painterResource(Res.drawable.blue_stopwatch_with_pink_arrow),
            contentDescription = null,
            modifier = Modifier
                .offset(x = 40.dp, y = 80.dp)
        )

        Image(
            painter = painterResource(Res.drawable.pie_chart),
            contentDescription = null,
            modifier = Modifier
                .offset(x = 30.dp, y = 220.dp)
                .size(45.dp)
        )

        // Calculator - top right
        Image(
            painter = painterResource(Res.drawable.blue_desk_calendar),
            contentDescription = null,
            modifier = Modifier
                .offset(x = 320.dp, y = 170.dp)
                .size(42.dp)
                .rotate(-15f)
        )

        // Papers - right middle
        Image(
            painter = painterResource(Res.drawable.multicolored_smartphone_notifications),
            contentDescription = null,
            modifier = Modifier
                .offset(x = 280.dp, y = 300.dp)
                .size(55.dp)
        )

        // Rocket - bottom left
        Image(
            painter = painterResource(Res.drawable.vase_with_tulips__glasses_and_pencil),
            contentDescription = null,
            modifier = Modifier
                .offset(x = 35.dp, y = 360.dp)
                .size(48.dp)
                .rotate(-20f)
        )
        ColoredDot(color = Color(0xFF87CEEB), x = 290.dp, y = 100.dp, size = 12.dp)
        ColoredDot(color = Color(0xFFB19CD9), x = 230.dp, y = 130.dp, size = 8.dp)
        ColoredDot(color = Color(0xFFFFB6C1), x = 160.dp, y = 480.dp, size = 10.dp)
        ColoredDot(color = Color(0xFF90EE90), x = 320.dp, y = 450.dp, size = 9.dp)
        ColoredDot(color = Color(0xFFFFE66D), x = 280.dp, y = 490.dp, size = 11.dp)
    }

    BlurredCircle(color = Color(0xFF87CEEB), x = 50.dp, y = maxHeight.value.dp/2, size = 100.dp)
    BlurredCircle(color = Color(0xFFFFB6C1), x = (maxWidth.value.div(1.5)).dp, y = (maxHeight.value.div(1.3)).dp, size = 100.dp)
    BlurredCircle(color = Color(0xFF90EE90), x = 0.dp-28.dp, y = (maxHeight.value/6).dp, size = 100.dp)
    BlurredCircle(color = Color(0xFFFFE66D), x = maxWidth.value.dp-160.dp, y =0.dp, size = 100.dp)


    // colored dots
    //blurred circles
}

@Composable
fun ColoredDot(color: Color, x: Dp, y: Dp, size: Dp) {
    Box(
        modifier = Modifier
            .offset(x = x, y = y)
            .size(size)
            .background(color, shape = CircleShape)
    )
}

@Composable
fun BlurredCircle(color: Color, x: Dp, y: Dp, size: Dp) {
    Box(
        modifier = Modifier
            .offset(x = x, y = y)
            .size(size)
            .blur(
                50.dp,
                edgeTreatment = BlurredEdgeTreatment.Unbounded
            )
            .background(color.copy(alpha = 0.5f), shape = CircleShape)

    )
}
