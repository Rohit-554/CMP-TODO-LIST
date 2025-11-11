package io.jadu.todoApp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import todo_list.composeapp.generated.resources.Manrope_Bold
import todo_list.composeapp.generated.resources.Manrope_ExtraBold
import todo_list.composeapp.generated.resources.Manrope_Medium
import todo_list.composeapp.generated.resources.Manrope_Regular
import todo_list.composeapp.generated.resources.Manrope_SemiBold
import todo_list.composeapp.generated.resources.Res

@Composable
fun manropeFamilyFont() = FontFamily(
    Font(Res.font.Manrope_Regular, FontWeight.Normal),
    Font(Res.font.Manrope_Medium, FontWeight.Medium),
    Font(Res.font.Manrope_SemiBold, FontWeight.SemiBold),
    Font(Res.font.Manrope_Bold, FontWeight.Bold),
    Font(Res.font.Manrope_ExtraBold, FontWeight.Black)
)

/**
 * Font size 32.sp
 */
@Composable
fun H1TextStyle() = TextStyle(
    fontFamily = manropeFamilyFont(),
    fontWeight = FontWeight.Bold,
    color = MaterialTheme.colorScheme.onSurface,
    fontSize = 32.sp,
)

/**
 * Font size 28.sp
 */

@Composable
fun H2TextStyle() = TextStyle(
    fontFamily = manropeFamilyFont(),
    fontWeight = FontWeight.Bold,
    color = MaterialTheme.colorScheme.onSurface,
    fontSize = 28.sp,
)

/**
 * Font size 24.sp
 */
@Composable
fun H3TextStyle() = TextStyle(
    fontFamily = manropeFamilyFont(),
    fontWeight = FontWeight.SemiBold,
    color = MaterialTheme.colorScheme.onSurface,
    fontSize = 24.sp,
)

/**
 * Font size 20.sp
 */

@Composable
fun BodyXXLarge() = TextStyle(
    fontFamily = manropeFamilyFont(),
    fontWeight = FontWeight.Medium,
    color = MaterialTheme.colorScheme.onSurface,
    fontSize = 20.sp,
)

/**
 * Font size 18.sp
 */
@Composable
fun BodyXLarge() = TextStyle(
    fontFamily = manropeFamilyFont(),
    fontWeight = FontWeight.Medium,
    color = MaterialTheme.colorScheme.onSurface,
    fontSize = 18.sp,
)

/**
 * Font size 16.sp
 */
@Composable
fun BodyLarge() = TextStyle(
    fontFamily = manropeFamilyFont(),
    fontWeight = FontWeight.Medium,
    color = MaterialTheme.colorScheme.onSurface,
    fontSize = 16.sp,
)

/**
 * Font size 14.sp
 */
@Composable
fun BodyNormal() = TextStyle(
    fontFamily = manropeFamilyFont(),
    fontWeight = FontWeight.Medium,
    color = MaterialTheme.colorScheme.onSurface,
    fontSize = 14.sp,
)

/**
 * Font size 12.sp
 */
@Composable
fun BodySmall() = TextStyle(
    fontFamily = manropeFamilyFont(),
    fontWeight = FontWeight.Medium,
    color = MaterialTheme.colorScheme.onSurface,
    fontSize = 12.sp,
)

/**
 * Font size 10.sp
 */
@Composable
fun BodyXSmall() = TextStyle(
    fontFamily = manropeFamilyFont(),
    fontWeight = FontWeight.Normal,
    color = MaterialTheme.colorScheme.onSurface,
    fontSize = 10.sp,
)