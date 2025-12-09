package io.jadu.todoApp.ui.screens.homescreen.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import io.jadu.todoApp.ui.theme.BodyNormal
import io.jadu.todoApp.ui.theme.Spacing
import io.jadu.todoApp.ui.uiutils.screenWidth
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

object AppSnackbarHost {
    val snackbarHostState = SnackbarHostState()
}

@OptIn(DelicateCoroutinesApi::class)
fun showSnackBar(
    message: String,
    duration: SnackbarDuration = SnackbarDuration.Short,
    positiveMessage: Boolean = false,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    var actionLabelLocal = actionLabel
    if (actionLabel == null && positiveMessage)
        actionLabelLocal = "POSITIVE"
    else if (actionLabel == null && !positiveMessage)
        actionLabelLocal = "NEGATIVE"

    GlobalScope.launch {
        AppSnackbarHost.snackbarHostState.showSnackbar(
            message,
            duration = duration,
            actionLabel = actionLabelLocal
        )
    }
}



@Composable
fun CustomSnackbarHost(
    snackbarHostState: SnackbarHostState = AppSnackbarHost.snackbarHostState,
    modifier: Modifier = Modifier
) {
    var showAnimation by remember { mutableStateOf(false) }
    var currentMessage by remember { mutableStateOf("") }
    var isSuccess by remember { mutableStateOf(true) }
    var animationDuration by remember { mutableStateOf(800L) }
    LaunchedEffect(snackbarHostState.currentSnackbarData) {
        snackbarHostState.currentSnackbarData?.let { data ->
            val actionLabel = data.visuals.actionLabel

            animationDuration = when (data.visuals.duration) {
                SnackbarDuration.Short -> 800L
                SnackbarDuration.Long -> 2000L
                SnackbarDuration.Indefinite -> 4000L
            }
            when (actionLabel) {
                "POSITIVE" -> {
                    isSuccess = true
                    currentMessage = data.visuals.message
                    showAnimation = true
                }

                "NEGATIVE" -> {
                    isSuccess = false
                    currentMessage = data.visuals.message
                    showAnimation = true
                }
            }
        }
    }

    BarfiSnackBar(
        message = currentMessage,
        isSuccess = isSuccess,
        isVisible = showAnimation,
        onAnimationComplete = {
            showAnimation = false
            snackbarHostState.currentSnackbarData?.dismiss()
        },
        onDismiss = {
            showAnimation = false
            snackbarHostState.currentSnackbarData?.dismiss()
        },
        modifier = modifier,
        displayDurationMillis = animationDuration
    )

}

@Composable
fun BarfiSnackBar(
    message: String = "Success",
    isSuccess: Boolean = true,
    isVisible: Boolean = true,
    onAnimationComplete: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    displayDurationMillis: Long = 800L
) {

    val circleOffsetX = remember { Animatable(0f) }
    val offsetY = remember { Animatable(-400f) }
    val offsetXDrag = remember { Animatable(0f) }
    val rectangleWidth = remember { Animatable(0f) }
    val circleScale = remember { Animatable(1f) }
    val rectangleAlpha = remember { Animatable(0f) }
    val glowIntensity = remember { Animatable(0f) }
    val checkmarkProgress = remember { Animatable(0f) }
    val rippleScale = remember { Animatable(0f) }
    val shadowOffset = remember { Animatable(0f) }
    val textAlpha = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()
    val screenWidth = screenWidth.value
    val circleDistance = -screenWidth * 0.35f
    val customOvershoot = CubicBezierEasing(0.25f, 0.46f, 0.45f, 0.94f)

    // Colors based on success/error
    val primaryColor = if (isSuccess) Color(0xFF4CAF50) else Color(0xFFE53E3E)
    val gradientColors = if (isSuccess) {
        listOf(Color(0xFF81C784), Color(0xFF4CAF50), Color(0xFF66BB6A))
    } else {
        listOf(Color(0xFFFF8A80), Color(0xFFE53E3E), Color(0xFFEF5350))
    }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            // Reset all values
            circleOffsetX.snapTo(0f)
            offsetY.snapTo(-400f)
            offsetXDrag.snapTo(0f)
            rectangleWidth.snapTo(0f)
            circleScale.snapTo(1f)
            rectangleAlpha.snapTo(0f)
            glowIntensity.snapTo(0f)
            checkmarkProgress.snapTo(0f)
            rippleScale.snapTo(0f)
            shadowOffset.snapTo(0f)

            // Animation sequence
            launch { rectangleAlpha.animateTo(0.3f, tween(200)) }
            launch { glowIntensity.animateTo(1f, tween(200)) }
            launch { shadowOffset.animateTo(8f, tween(150)) }
            checkmarkProgress.animateTo(1f, tween(80, easing = FastOutLinearInEasing))
            offsetY.animateTo(0f, spring(dampingRatio = 0.7f, stiffness = 400f))

            delay(200)

            launch {
                circleScale.animateTo(1.1f, tween(80, easing = FastOutSlowInEasing))
                delay(40)
                circleScale.animateTo(1f, tween(120, easing = LinearOutSlowInEasing))
            }

            launch {
                rectangleWidth.animateTo(
                    targetValue = screenWidth*0.8f,
                    animationSpec = spring(dampingRatio = 0.8f, stiffness = 300f)
                )
                textAlpha.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 300)
                )
            }


            launch {
                rectangleAlpha.animateTo(1f, tween(400, easing = FastOutSlowInEasing))
            }

            circleOffsetX.animateTo(circleDistance, spring(dampingRatio = 0.9f, stiffness = 350f))

            delay(600)

            launch {
                rippleScale.animateTo(2f, tween(400, easing = LinearOutSlowInEasing))
                rippleScale.animateTo(0f, tween(80))
            }

            delay(displayDurationMillis)

            launch {
                textAlpha.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 200)
                )
            }
            delay(200)

            launch {
                circleScale.animateTo(0.9f, tween(80))
                circleScale.animateTo(1f, tween(150, easing = customOvershoot))
            }

            launch {
                rectangleWidth.animateTo(0f, spring(dampingRatio = 0.9f, stiffness = 300f))
            }
            delay(25)
            circleOffsetX.animateTo(0f, spring(dampingRatio = 0.8f, stiffness = 400f))


            launch {
                rectangleAlpha.animateTo(0f, tween(200))
                glowIntensity.animateTo(0f, tween(200))
            }

            checkmarkProgress.animateTo(0f, tween(150))

            launch {
                circleScale.animateTo(0.8f, tween(120))
                shadowOffset.animateTo(0f, tween(120))
            }
            offsetY.animateTo(-400f, spring(dampingRatio = 1.0f, stiffness = 500f))

            delay(300)
            onAnimationComplete?.invoke()
        }
    }

    if (isVisible) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .offset {
                    IntOffset(
                        offsetXDrag.value.roundToInt(),
                        offsetY.value.roundToInt()
                    )
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            coroutineScope.launch {
                                if (abs(offsetXDrag.value) > 200f) {
                                    // Dismiss with slide animation
                                    val targetX =
                                        if (offsetXDrag.value > 0) size.width.toFloat() else -size.width.toFloat()
                                    offsetXDrag.animateTo(
                                        targetX,
                                        tween(300, easing = FastOutSlowInEasing)
                                    )
                                    onDismiss?.invoke()
                                } else {
                                    // Snap back to center
                                    offsetXDrag.animateTo(
                                        0f,
                                        spring(dampingRatio = 0.7f, stiffness = 400f)
                                    )
                                }
                            }
                        }
                    ) { _, dragAmount ->
                        coroutineScope.launch {
                            offsetXDrag.snapTo(offsetXDrag.value + dragAmount.x)
                        }
                    }
                },
            contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier
                    .height(60.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                // Ripple effect
                if (rippleScale.value > 0f) {
                    Box(
                        modifier = Modifier
                            .size((60 * rippleScale.value).dp)
                            .clip(CircleShape)
                            .background(
                                primaryColor.copy(
                                    alpha = (1f - rippleScale.value / 2f).coerceAtLeast(0f)
                                )
                            )
                    )
                }

                // Rectangle with text
                Box(
                    modifier = Modifier
                        .width(rectangleWidth.value.dp)
                        .offset { IntOffset(x = 2.dp.roundToPx(), y = 6.dp.roundToPx()) }
                        .height(48.dp)
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(Spacing.s10),
                            clip = true
                        )
                        .clip(RoundedCornerShape(Spacing.s10))
                        .background(
                            Brush.horizontalGradient(
                                colors = gradientColors.map { it.copy(alpha = rectangleAlpha.value) }
                            )
                        )
                ) {
                    Text(
                        message,
                        style = BodyNormal(),
                        color = Color.White,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 55.dp, end = 8.dp)
                            .alpha(textAlpha.value)
                    )
                }


                // White circle with icon
                Box(
                    modifier = Modifier
                        .scale(circleScale.value)
                        .size(60.dp)
                        .offset(
                            x = circleOffsetX.value.dp,
                            y = shadowOffset.value.dp / 2
                        )
                        .zIndex(1f)
                        .shadow(
                            elevation = 8.dp,
                            shape = CircleShape,
                            clip = true
                        )
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(Color.White, Color(0xFFF5F5F5))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (checkmarkProgress.value > 0f) {
                        Canvas(modifier = Modifier.size(24.dp)) {
                            val path = Path()
                            val progress = checkmarkProgress.value
                            val strokeWidth = 3.dp.toPx()

                            if (isSuccess) {
                                // Checkmark
                                path.moveTo(size.width * 0.2f, size.height * 0.5f)
                                if (progress > 0.5f) {
                                    path.lineTo(size.width * 0.45f, size.height * 0.7f)
                                } else {
                                    path.lineTo(
                                        size.width * (0.2f + (progress * 0.5f)),
                                        size.height * (0.5f + (progress * 0.4f))
                                    )
                                }

                                if (progress > 0.5f) {
                                    val secondProgress = (progress - 0.5f) * 2f
                                    path.lineTo(
                                        size.width * (0.45f + (secondProgress * 0.35f)),
                                        size.height * (0.7f - (secondProgress * 0.4f))
                                    )
                                }
                            } else {
                                // X mark
                                val currentProgress = progress.coerceAtMost(1f)
                                // First line
                                path.moveTo(size.width * 0.25f, size.height * 0.25f)
                                path.lineTo(
                                    size.width * (0.25f + (currentProgress * 0.5f)),
                                    size.height * (0.25f + (currentProgress * 0.5f))
                                )
                                // Second line
                                path.moveTo(size.width * 0.75f, size.height * 0.25f)
                                path.lineTo(
                                    size.width * (0.75f - (currentProgress * 0.5f)),
                                    size.height * (0.25f + (currentProgress * 0.5f))
                                )
                            }

                            drawPath(
                                path = path,
                                color = primaryColor,
                                style = Stroke(
                                    width = strokeWidth,
                                    cap = StrokeCap.Round,
                                    join = StrokeJoin.Round
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
