package io.jadu.todoApp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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