package io.jadu.todoApp.ui.animatedBottomBar

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.jadu.todoApp.ui.animatedBottomBar.models.CurveAnimationType
import io.jadu.todoApp.ui.animatedBottomBar.util.toPx
import kotlin.plus

@Composable
fun CurvedNavigationBar(
    selectedIndex: Int,
    itemCount: Int,
    animationType: CurveAnimationType = CurveAnimationType.SMOOTH,
    backgroundColor: Color = Color.White,
    curveRadius: Dp = 54.dp,
    curveDepth: Dp = 30.dp,
    curveSmoothness: Dp = 25.dp,
    navBarHeight: Dp = 56.dp,
    animationDuration: Int = 300,
    modifier: Modifier = Modifier
) {
    var previousIndex by remember { mutableStateOf(selectedIndex) }

    val animatedIndex by animateFloatAsState(
        targetValue = selectedIndex.toFloat(),
        animationSpec = when (animationType) {
            CurveAnimationType.SMOOTH -> tween(
                durationMillis = animationDuration,
                easing = FastOutSlowInEasing
            )
            CurveAnimationType.DYNAMIC -> spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
            CurveAnimationType.BOUNCY -> spring(
                dampingRatio = 0.7f,
                stiffness = Spring.StiffnessLow
            )
        },
        label = "curve_position"
    )

    val animatedDepth by animateFloatAsState(
        targetValue = when (animationType) {
            CurveAnimationType.SMOOTH -> curveDepth.toPx()
            CurveAnimationType.DYNAMIC -> {
                if (selectedIndex != previousIndex) (curveDepth + 5.dp).toPx()
                else curveDepth.toPx()
            }
            CurveAnimationType.BOUNCY -> curveDepth.toPx()
        },
        animationSpec = when (animationType) {
            CurveAnimationType.SMOOTH -> tween(animationDuration, easing = FastOutSlowInEasing)
            CurveAnimationType.DYNAMIC -> spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessMedium
            )
            CurveAnimationType.BOUNCY -> spring(
                dampingRatio = 0.6f,
                stiffness = Spring.StiffnessMedium
            )
        },
        finishedListener = {
            previousIndex = selectedIndex
        },
        label = "curve_depth"
    )

    val animatedRadius by animateFloatAsState(
        targetValue = curveRadius.toPx(),
        animationSpec = when (animationType) {
            CurveAnimationType.SMOOTH, CurveAnimationType.DYNAMIC -> tween(
                durationMillis = animationDuration,
                easing = FastOutSlowInEasing
            )
            CurveAnimationType.BOUNCY -> spring(
                dampingRatio = 0.6f,
                stiffness = Spring.StiffnessMedium
            )
        },
        label = "curve_radius"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(navBarHeight)
    ) {
        val cellWidth = size.width / itemCount
        val centerX = cellWidth * animatedIndex + cellWidth / 2

        val radius = animatedRadius
        val depth = animatedDepth
        val smoothness = curveSmoothness.toPx()

        val path = Path().apply {
            moveTo(0f, size.height)
            lineTo(0f, 0f)
            lineTo(centerX - radius, 0f)

            cubicTo(
                centerX - radius + smoothness, 0f,
                centerX - smoothness, depth,
                centerX, depth
            )

            cubicTo(
                centerX + smoothness, depth,
                centerX + radius - smoothness, 0f,
                centerX + radius, 0f
            )

            lineTo(size.width, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }

        drawPath(
            path = path,
            color = backgroundColor,
            style = Fill
        )
    }
}