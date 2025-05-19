package pl.golem.spacenews.helpers

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.char

val timestamp_24h = LocalDateTime.Format {
    year()
    char('-')
    monthNumber()
    char('-')
    dayOfMonth()
    char(' ')
    hour()
    char(':')
    minute()
    char(':')
    second()
}
val timestamp_amPm = LocalDateTime.Format {
    year()
    char('-')
    monthNumber()
    char('-')
    dayOfMonth()
    char(' ')
    amPmHour()
    char(':')
    minute()
    char(':')
    second()
    char(' ')
    amPmMarker("AM", "PM")
}