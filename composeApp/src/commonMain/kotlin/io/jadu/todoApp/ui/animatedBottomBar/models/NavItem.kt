package io.jadu.todoApp.ui.animatedBottomBar.models

import androidx.compose.ui.graphics.vector.ImageVector
import io.jadu.todoApp.ui.navigation.NavRoute
import org.jetbrains.compose.resources.DrawableResource



/**
 * Data class representing a navigation item in the bottom bar.
 * @param icon The primary icon to display for this navigation item
 * @param selectedIcon Optional icon to display when the item is selected
 * @param label The text label for this navigation item
 * @param route The navigation route associated with this item
 * @param badgeCount Optional badge count to display on the icon
 */
data class NavItem(
    val icon: IconSource,
    val selectedIcon: IconSource? = null,
    val label: String,
    val route: NavRoute,
    val badgeCount: Int? = null
)

/**
 * Drawable resource-based icon.
 * @param resId The DrawableResource reference
 */
sealed class IconSource {
    data class Vector(val imageVector: ImageVector) : IconSource()
    data class Drawable(val resId: DrawableResource) : IconSource()
}