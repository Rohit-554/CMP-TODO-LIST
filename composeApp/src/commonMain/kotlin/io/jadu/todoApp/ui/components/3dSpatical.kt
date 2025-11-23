package io.jadu.todoApp.ui.components

import org.jetbrains.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.compose.ui.graphics.graphicsLayer
import io.jadu.todoApp.ui.theme.BodyXSmall
import io.jadu.todoApp.ui.theme.Spacing
import io.jadu.todoApp.ui.theme.TodoColors
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import todo_list.composeapp.generated.resources.Res
import todo_list.composeapp.generated.resources.book
import todo_list.composeapp.generated.resources.briefcase
import todo_list.composeapp.generated.resources.calendar
import todo_list.composeapp.generated.resources.clock_loader_icon
import todo_list.composeapp.generated.resources.close_up_of_pink_coffee_cup
import todo_list.composeapp.generated.resources.document_text
import todo_list.composeapp.generated.resources.female_sitting_on_the_floor_with_cup_in_hand_and_laptop_on_leg
import todo_list.composeapp.generated.resources.home
import todo_list.composeapp.generated.resources.multicolored_smartphone_notifications
import todo_list.composeapp.generated.resources.notification
import todo_list.composeapp.generated.resources.profile_2user
import todo_list.composeapp.generated.resources.user_octagon
import todo_list.composeapp.generated.resources.vase_with_tulips__glasses_and_pencil
import kotlin.collections.List
import kotlin.math.*
import kotlin.random.Random

private data class Vec3(val x: Float, val y: Float, val z: Float)

private fun sphericalToCartesian(radius: Float, theta: Float, phi: Float): Vec3 {
    val st = sin(theta)
    val x = radius * st * cos(phi)
    val y = radius * cos(theta)
    val z = radius * st * sin(phi)
    return Vec3(x, y, z)
}

private fun rotateY(p: Vec3, angle: Float): Vec3 {
    val c = cos(angle)
    val s = sin(angle)
    return Vec3(
        p.x * c + p.z * s,
        p.y,
        -p.x * s + p.z * c
    )
}

private fun rotateX(p: Vec3, angle: Float): Vec3 {
    val c = cos(angle)
    val s = sin(angle)
    return Vec3(
        p.x,
        p.y * c - p.z * s,
        p.y * s + p.z * c
    )
}

private fun project(p: Vec3, fov: Float, cx: Float, cy: Float): Pair<Float, Float> {
    val totalDistance = p.z + fov
    val scale = fov / totalDistance  // field_of_view.div(totaDistance)
    val x2 = p.x * scale + cx
    val y2 = p.y * scale + cy
    return x2 to y2
}

data class SphereLabel(val text: String, val icon: DrawableResource)

@Composable
@Preview
fun SphereTextDemo(
    modifier: Modifier = Modifier.fillMaxSize(),
    radius: Float = 400f,
    labels: List<SphereLabel> = defaultLabels()
) {
    var cameraYaw by remember { mutableStateOf(0f) }
    var cameraPitch by remember { mutableStateOf(0f) }



    val autoRotate = remember { mutableStateOf(0f) }
    LaunchedEffect(Unit) {
        while (true) {
            autoRotate.value = (autoRotate.value + 0.0025f) % (2 * PI.toFloat())
            delay(16)
        }
    }

    /**
     *   `Fibonacci sphere algorithm`
     *
     *   i - index , n = total number of elements
     *   PHI (φ) - Vertical angle:
     *   φᵢ = arccos(1 - 2zᵢ)
     *    where: zᵢ = (i + 0.5) / n
     *
     *   THETA (θ) - Horizontal angle:
     *   θᵢ = π(1 + √5) × i
     *
     * */

    // Automatically assign positions to labels in a distributed pattern
    val labelsWithPositions = remember(labels) {
        labels.mapIndexed { index, label ->
            val totalLabels = labels.size
            // Distribute labels evenly across the sphere using Fibonacci sphere algorithm
            val phi = acos(1 - 2 * (index + 0.5f) / totalLabels)
            val theta = PI.toFloat() * (1 + sqrt(5f)) * (index + 1f)
            Triple(label, theta, phi)
        }
    }

    // Generate random colorful dots on sphere surface
    val randomDots = remember {
        List(80) {
            val theta = Random.nextFloat() * 2 * PI.toFloat()
            val phi = Random.nextFloat() * PI.toFloat()
            val color = listOf(
                TodoColors.Pink.color,
                TodoColors.Blue.color,
                TodoColors.Emerald.color,
                TodoColors.Orange.color,
                TodoColors.Orchid.color,
                TodoColors.Turquoise.color,
                TodoColors.Gold.color,
                TodoColors.DarkPurple.color
            ).random()
            val size = 3f + Random.nextFloat() * (8f - 3f)
            Triple(theta, phi, Pair(color, size))
        }
    }

    val textStyle = BodyXSmall()
    val textMeasurer = rememberTextMeasurer()

    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    val (dx, dy) = dragAmount
                    cameraYaw += dx * 0.008f
                    cameraPitch += dy * 0.008f
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)

            // Outer glow rings
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        TodoColors.LightPrimary.color.copy(alpha = 0.3f),
                        TodoColors.LightestBlue.color.copy(alpha = 0.2f),
                        TodoColors.SoftCyan.color.copy(alpha = 0.1f),
                        Color.Transparent
                    ),
                    center = center,
                    radius = radius + 60f
                ),
                radius = radius + 60f,
                center = center
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val widthPx = with(LocalDensity.current) { maxWidth.toPx() }
                val heightPx = with(LocalDensity.current) { maxHeight.toPx() }
                val cx = widthPx / 2f
                val cy = heightPx / 2f
                val fov = 800f

                // Render colorful dots
                randomDots.forEach { (theta, phi, colorData) ->
                    val (color, size) = colorData
                    val p = sphericalToCartesian(radius, theta, phi)
                    val rotated = rotateX(rotateY(p, cameraYaw + autoRotate.value), cameraPitch)

                    if (rotated.z + fov <= 0.001f) return@forEach

                    val (x2, y2) = project(rotated, fov, cx, cy)
                    val scale = fov / (rotated.z + fov)
                    val depth = rotated.z
                    val dotAlpha = ((scale - 0.2f) / (1f - 0.2f)).coerceIn(0.2f, 1f)
                    val dotSize = (0.2f + (size * scale - 2f) / (6f - 2f) * (0.6f - 0.2f)).coerceIn(0.2f, 0.6f)

                    Canvas(modifier = Modifier
                        .offset { IntOffset(x2.roundToInt(), y2.roundToInt()) }
                        .size(dotSize.dp)
                        .zIndex(-depth)
                    ) {
                        drawCircle(
                            color = color.copy(alpha = dotAlpha * 0.4f),
                            radius = this.size.width / 2f + 4f
                        )
                        drawCircle(
                            color = color.copy(alpha = dotAlpha),
                            radius = this.size.width / 2f
                        )
                    }
                }

                val projected = labelsWithPositions.map { (label, theta, phi) ->
                    val p = sphericalToCartesian(radius, theta, phi)
                    val rotated = rotateX(rotateY(p, cameraYaw + autoRotate.value), cameraPitch)
                    val depth = rotated.z
                    val (x2, y2) = project(rotated, fov, cx, cy)
                    val scale = fov / (rotated.z + fov)
                    Triple(label, LabelRenderInfo(rotated, x2, y2, depth, scale), label.text)
                }.sortedBy { it.second.depth }

                for ((index, triple) in projected.withIndex()) {
                    val label = triple.first
                    val info = triple.second
                    val text = triple.third

                    if (info.rotated.z + fov <= 0.001f) continue

                    val measured = textMeasurer.measure(
                        text = text,
                        style = textStyle
                    )
                    val textW = measured.size.width.toFloat()
                    val textH = measured.size.height.toFloat()

                    val baseScale = (0.6f + 0.8f * info.scale).coerceIn(0.4f, 1.8f)
                    val alpha = ((info.scale - 0.2f) / (1f - 0.2f)).coerceIn(0.1f, 1f)

                    val posX = info.x - textW / 2f
                    val posY = info.y - textH / 2f

                    val zInd = -info.depth

                    Box(
                        modifier = Modifier
                            .offset { IntOffset(posX.roundToInt(), posY.roundToInt()) }
                            .graphicsLayer {
                                transformOrigin = TransformOrigin(0.5f, 0.5f)
                                scaleX = baseScale
                                scaleY = baseScale
                                this.alpha = alpha
                            }
                            .zIndex(zInd)
                    ) {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(Spacing.s1Half))
                                .border(
                                    width = Spacing.sHalf / 2,
                                    color = TodoColors.BorderGrey.color,
                                    shape = RoundedCornerShape(Spacing.s1Half)
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(Spacing.default)
                        ) {
                            // Left side (icon) - darker background
                            Box(
                                modifier = Modifier
                                    .background(color = TodoColors.Primary.color)
                                    .padding(horizontal = Spacing.s2, vertical = Spacing.s1Half),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(label.icon),
                                    contentDescription = text,
                                    modifier = Modifier.size(Spacing.s3Half),
                                    colorFilter = ColorFilter.tint(TodoColors.White.color)
                                )
                            }

                            // Right side (text) - lighter background
                            Box(
                                modifier = Modifier
                                    .background(color = TodoColors.LightPrimary.color)
                                    .padding(horizontal = Spacing.s2Half, vertical = Spacing.s1Half),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = text,
                                    style = textStyle.copy(
                                        color = TodoColors.Dark.color,
                                        fontWeight = FontWeight.Medium,
                                        letterSpacing = 0.2.sp
                                    )
                                )
                            }
                        }
                    }
                }

                Text(
                    text = "Drag to rotate",
                    style = TextStyle(
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = TodoColors.SkyBlue.color.copy(alpha = 0.5f),
                        letterSpacing = 0.8.sp
                    ),
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = Spacing.s6)
                )
            }
        }
    }
}

private data class LabelRenderInfo(
    val rotated: Vec3,
    val x: Float,
    val y: Float,
    val depth: Float,
    val scale: Float
)

private fun defaultLabels(): List<SphereLabel> {
    return listOf(
        SphereLabel("Office", Res.drawable.briefcase),
        SphereLabel("Personal", Res.drawable.user_octagon),
        SphereLabel("Books", Res.drawable.book),
        SphereLabel("Shopping", Res.drawable.calendar),
        SphereLabel("Health", Res.drawable.document_text),
        SphereLabel("Projects", Res.drawable.multicolored_smartphone_notifications),
        SphereLabel("Home", Res.drawable.home),
        SphereLabel("Travel", Res.drawable.close_up_of_pink_coffee_cup)
    )
}

