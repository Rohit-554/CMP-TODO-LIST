package io.jadu.todoApp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.minusMonths
import com.kizitonwose.calendar.core.now
import com.kizitonwose.calendar.core.plusMonths
import io.jadu.todoApp.ui.theme.BodySmall
import io.jadu.todoApp.ui.theme.BodyXLarge
import io.jadu.todoApp.ui.theme.H3TextStyle
import io.jadu.todoApp.ui.theme.Spacing
import io.jadu.todoApp.ui.theme.TodoColors
import io.jadu.todoApp.ui.uiutils.VSpacer
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.YearMonth
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
@Preview
fun MonthCalendar(
    selectedDate: LocalDate? = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    onDateSelected: (LocalDate) -> Unit = {}
) {
    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(100) } // Adjust as needed
    val endMonth = remember { currentMonth.plusMonths(100) } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library
    val coroutineScope = rememberCoroutineScope()
    val daysOfWeek = remember { daysOfWeek() }
    val state =
        rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = firstDayOfWeek
        )

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        VSpacer(8.dp)
        Box(
            modifier =
                Modifier
                    .background(Color.White, shape = RoundedCornerShape(Spacing.s6))
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier) {
                SimpleCalendarTitle(
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
                    currentMonth = state.firstVisibleMonth,
                    goToPrevious = {
                        coroutineScope.launch {
                            state.animateScrollToMonth(
                                state.firstVisibleMonth.yearMonth.minusMonths(1)
                            )
                        }
                    },
                    goToNext = {
                        coroutineScope.launch {
                            state.animateScrollToMonth(
                                state.firstVisibleMonth.yearMonth.plusMonths(1)
                            )
                        }
                    },
                )
                DaysOfWeekTitle(daysOfWeek = daysOfWeek)
                HorizontalCalendar(
                    state = state,
                    dayContent = { day ->
                        Day(
                            day,
                            isSelected = selectedDate == day.date,
                        ) { clickedDay ->
                            onDateSelected(clickedDay.date)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SimpleCalendarTitle(
    modifier: Modifier,
    currentMonth: CalendarMonth,
    isHorizontal: Boolean = true,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit,
) {
    Row(
        modifier = modifier.height(40.dp),
        verticalAlignment = CenterVertically,
    ) {
        CalendarNavigationIcon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "Previous",
            onClick = goToPrevious
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .testTag("MonthTitle"),
            text = currentMonth.yearMonth.month.toString() + " - '" + currentMonth.yearMonth.year.toString()
                .takeLast(2),
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            style = H3TextStyle()
        )

        CalendarNavigationIcon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Next",
            onClick = goToNext,
        )

    }
}

@Composable
private fun CalendarNavigationIcon(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) =
    Box(
        modifier =
            Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(shape = CircleShape)
                .clickable(role = Role.Button, onClick = onClick),
    ) {
        Icon(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(4.dp)
                    .align(Alignment.Center),
            imageVector = imageVector,
            contentDescription = contentDescription,
        )
    }


@Composable
fun Day(day: CalendarDay, isSelected: Boolean, onClick: (CalendarDay) -> Unit) {
    val textColor =
        when (day.position) {
            DayPosition.MonthDate -> if (isSelected) TodoColors.Primary.color else Color.Unspecified
            DayPosition.InDate, DayPosition.OutDate ->
                MaterialTheme.colorScheme.secondary.copy(alpha = 0.45f)
        }
    val borderColor =
        when (day.position) {
            DayPosition.MonthDate ->
                if (isSelected) Color.Transparent
                else MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)

            DayPosition.InDate, DayPosition.OutDate -> Color.Transparent
        }

    Box(
        modifier =
            Modifier
                .padding(2.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(24f))
                .border(
                    BorderStroke(1.dp, borderColor),
                    RoundedCornerShape(24f)
                )
                .background(
                    color =
                        if (isSelected) TodoColors.LightPrimary.color
                        else Color.Transparent
                )
                .clickable(
                    enabled = day.position == DayPosition.MonthDate,
                    onClick = { onClick(day) }
                ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.day.toString(),
            style = BodyXLarge(),
            color = textColor,
            fontWeight = SemiBold
        )
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = BodySmall(),
                text = dayOfWeek.name.take(3),
            )
        }
    }
}
