package io.jadu.todoApp.ui.animatedBottomBar

/* Created by Rohit, On 27th Oct 2025 - 2:08 AM */

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.jadu.todoApp.ui.animatedBottomBar.models.CurveAnimationType
import io.jadu.todoApp.ui.animatedBottomBar.models.NavItem
import io.jadu.todoApp.ui.animatedBottomBar.util.RenderIcon
import io.jadu.todoApp.ui.theme.TodoColors
import kotlinx.coroutines.launch

/**
 * Curved bottom navigation composable.
 *
 * @param items List of navigation items to display in the bar.
 * @param selectedIndex Index of the currently selected item.
 * @param modifier Modifier applied to the root composable container.
 * @param bottomBarAlignment Alignment of the bottom bar within the parent container.
 * @param curveAnimationType Animation style used for the curved cutout movement.
 * @param animationDuration Duration (ms) used by animations.
 * @param fabBackgroundColor Background color of the floating action button (FAB).
 * @param fabIconTint Tint color applied to the FAB icon.
 * @param fabDotColor Color of the optional dot/indicator shown under the FAB.
 * @param navBarBackgroundColor Background color of the navigation bar.
 * @param unselectedIconTint Tint color for icons when not selected.
 * @param unselectedTextColor Text color for labels when not selected.
 * @param fabSize Diameter size of the FAB.
 * @param fabIconSize Size of the icon displayed inside the FAB.
 * @param fabDotSize Size of the small indicator dot below the FAB.
 * @param navBarHeight Height of the navigation bar.
 * @param unselectedIconSize Size of icons when not selected.
 * @param unselectedTextSize Font size used for unselected labels.
 * @param fabElevation Elevation applied to the FAB.
 * @param curveRadius Horizontal radius of the curved cutout in the bar.
 * @param curveDepth Vertical depth of the curved cutout.
 * @param curveSmoothness Control distance for the bezier handles (smoothness).
 * @param showDot Whether to render the small dot indicator under the FAB.
 * @param showLabels Whether to show text labels under nav icons.
 * @param enableHapticFeedback Enable haptic feedback on item selection.
 * @param onItemSelected Callback invoked with the selected item index.
 */
@Composable
fun CurvedBottomNavigation(
    modifier: Modifier = Modifier.fillMaxSize(),
    items: List<NavItem>,
    selectedIndex: Int,
    bottomBarAlignment: Alignment = Alignment.BottomCenter,
    curveAnimationType: CurveAnimationType = CurveAnimationType.SMOOTH,
    animationDuration: Int = 300,
    fabBackgroundColor: Color = TodoColors.Primary.color,
    fabIconTint: Color = Color.White,
    fabDotColor: Color = Color(0xFF4CAF50),
    navBarBackgroundColor: Color = TodoColors.LightPrimary.color,
    unselectedIconTint: Color = TodoColors.Primary.color,
    unselectedTextColor: Color = Color.Gray,
    fabSize: Dp = 56.dp,
    fabIconSize: Dp = 28.dp,
    fabDotSize: Dp = 6.dp,
    enableFabIconScale: Boolean = false,
    navBarHeight: Dp = 56.dp,
    unselectedIconSize: Dp = 24.dp,
    unselectedTextSize: TextUnit = 11.sp,
    fabElevation: Dp = 12.dp,
    curveRadius: Dp = 54.dp,
    curveDepth: Dp = 30.dp,
    curveSmoothness: Dp = 25.dp,
    showDot: Boolean = true,
    showLabels: Boolean = true,
    enableHapticFeedback: Boolean = false,
    onItemSelected: (Int) -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = bottomBarAlignment
    ) {
        val componentWidth = maxWidth

        CurvedBottomNavigationContent(
            items = items,
            selectedIndex = selectedIndex,
            onItemSelected = onItemSelected,
            componentWidth = componentWidth,
            fabBackgroundColor = fabBackgroundColor,
            fabIconTint = fabIconTint,
            fabDotColor = fabDotColor,
            navBarBackgroundColor = navBarBackgroundColor,
            unselectedIconTint = unselectedIconTint,
            unselectedTextColor = unselectedTextColor,
            fabSize = fabSize,
            fabIconSize = fabIconSize,
            fabDotSize = fabDotSize,
            navBarHeight = navBarHeight,
            unselectedIconSize = unselectedIconSize,
            unselectedTextSize = unselectedTextSize,
            fabElevation = fabElevation,
            curveRadius = curveRadius,
            curveDepth = curveDepth,
            curveSmoothness = curveSmoothness,
            showDot = showDot,
            showLabels = showLabels,
            curveAnimationType = curveAnimationType,
            animationDuration = animationDuration,
            enableHapticFeedback = enableHapticFeedback,
            enableFabIconScale = enableFabIconScale
        )
    }
}

@Composable
private fun CurvedBottomNavigationContent(
    items: List<NavItem>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    componentWidth: Dp,
    fabBackgroundColor: Color,
    fabIconTint: Color,
    fabDotColor: Color,
    navBarBackgroundColor: Color,
    unselectedIconTint: Color,
    unselectedTextColor: Color,
    fabSize: Dp,
    fabIconSize: Dp,
    fabDotSize: Dp,
    navBarHeight: Dp,
    unselectedIconSize: Dp,
    unselectedTextSize: TextUnit,
    fabElevation: Dp,
    curveRadius: Dp,
    curveDepth: Dp,
    curveSmoothness: Dp,
    showDot: Boolean,
    showLabels: Boolean,
    curveAnimationType: CurveAnimationType,
    animationDuration: Int,
    enableHapticFeedback: Boolean,
    enableFabIconScale: Boolean,
) {
    val haptic = LocalHapticFeedback.current
    var currentIndex by remember { mutableStateOf(selectedIndex) }
    var previousIndex by remember { mutableStateOf(selectedIndex) }

    LaunchedEffect(selectedIndex) {
        if (selectedIndex != currentIndex) {
            previousIndex = currentIndex
            currentIndex = selectedIndex
        }
    }

    val screenWidth = componentWidth
    val cellWidth = screenWidth / items.size

    val startX = cellWidth * previousIndex + (cellWidth / 2) - (fabSize / 2)
    val endX = cellWidth * currentIndex + (cellWidth / 2) - (fabSize / 2)

    val fabX = remember { Animatable(startX.value) }
    val fabY = remember { Animatable(-35f) }
    val iconScale = remember { Animatable(1f) }
    // New: track which icon is shown inside FAB, separate from currentIndex
    var displayedIconIndex by remember { mutableStateOf(currentIndex) }

    LaunchedEffect(currentIndex) {
        if (currentIndex != previousIndex) {
            val startXValue = startX.value
            val endXValue = endX.value
            val distance = endXValue - startXValue
            var iconSwitched = false

            launch {
                fabX.animateTo(
                    targetValue = endXValue,
                    animationSpec = tween(animationDuration, easing = FastOutSlowInEasing)
                ) {
                    val progress = if (distance != 0f) {
                        (this.value - startXValue) / distance
                    } else {
                        0f
                    }
                    // Switch the FAB icon after ~30% progression to avoid icon-first change
                    if (!iconSwitched && progress >= 0.3f) {
                        displayedIconIndex = currentIndex
                        iconSwitched = true
                    }

                    val curveHeight = 40f
                    val parabolicY = -4f * progress * (progress - 1f)
                    val targetY = -30f + curveHeight * parabolicY

                    launch {
                        fabY.snapTo(targetY)
                    }
                }
                // Ensure icon is updated at the end if progress never reached threshold
                if (!iconSwitched) {
                    displayedIconIndex = currentIndex
                    iconSwitched = true
                }
            }
        }
    }

    LaunchedEffect(currentIndex) {
        iconScale.animateTo(
            targetValue = 1.25f,
            animationSpec = tween(durationMillis = 140, easing = FastOutSlowInEasing)
        )
        iconScale.animateTo(
            targetValue = 1f,
            animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow)
        )
    }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        // FAB
        FloatingActionButton(
            onClick = { },
            containerColor = fabBackgroundColor,
            shape = RoundedCornerShape(fabSize),
            elevation = FloatingActionButtonDefaults.elevation(fabElevation),
            modifier = Modifier
                .offset(x = fabX.value.dp, y = fabY.value.dp)
                .size(fabSize)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                RenderIcon(
                    // Use displayedIconIndex so icon switches in sync with FAB movement
                    iconSource = items[displayedIconIndex].selectedIcon
                        ?: items[displayedIconIndex].icon,
                    contentDescription = null,
                    tint = fabIconTint,
                    modifier = Modifier.size(if(enableFabIconScale)(fabIconSize.value * iconScale.value).dp else fabIconSize)
                )

                if (showDot) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .size(fabDotSize)
                            .background(fabDotColor, CircleShape)
                    )
                }
            }
        }

        // Curved Navigation Bar
        CurvedNavigationBar(
            selectedIndex = currentIndex,
            itemCount = items.size,
            animationType = curveAnimationType,
            backgroundColor = navBarBackgroundColor,
            curveRadius = curveRadius,
            curveDepth = curveDepth,
            curveSmoothness = curveSmoothness,
            navBarHeight = navBarHeight,
            animationDuration = animationDuration
        )

        // Bottom Nav Items
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(navBarHeight)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                BottomNavItem(
                    item = item,
                    isSelected = index == currentIndex,
                    onClick = {
                        if (currentIndex != index) {
                            if (enableHapticFeedback) haptic.performHapticFeedback(
                                HapticFeedbackType.Confirm
                            )
                            previousIndex = currentIndex
                            currentIndex = index
                            onItemSelected(index)
                        }
                    },
                    modifier = Modifier.weight(1f),
                    iconTint = unselectedIconTint,
                    textColor = unselectedTextColor,
                    iconSize = unselectedIconSize,
                    textSize = unselectedTextSize,
                    showLabel = showLabels
                )
            }
        }
    }
}




/*
* Raat ke baj rahe chaar,
kuch to hai is baar...
dil mein hai yaadein uski,
aur aankhon mein uska intezaar... 💫💖
*
* */
