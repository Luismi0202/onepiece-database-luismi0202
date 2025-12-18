package com.example.backend_luismi.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

data class TimezoneConversionRequest(
    val time: String,
    val date: String? = null
)

data class TimezoneConversionResponse(
    val ukTime: String,
    val spainTime: String,
    val timeDifference: String
)

@RestController
@RequestMapping("/api/timezone")
class TimezoneController {

    private val ukZone = ZoneId.of("Europe/London")
    private val spainZone = ZoneId.of("Europe/Madrid")

    @GetMapping("/convert-uk-to-spain")
    fun convertUkToSpain(
        @RequestParam(required = false, defaultValue = "00:00") time: String,
        @RequestParam(required = false) date: String?
    ): ResponseEntity<TimezoneConversionResponse> {
        try {
            // If no date is provided, use tomorrow's date (the 19th as mentioned in the problem)
            val localDateTime = if (date != null) {
                LocalDateTime.parse("${date}T${time}")
            } else {
                // Use tomorrow's date at the specified time
                LocalDateTime.now().plusDays(1).toLocalDate().atTime(parseTime(time))
            }

            // Create a ZonedDateTime for UK timezone
            val ukZonedTime = ZonedDateTime.of(localDateTime, ukZone)
            
            // Convert to Spain timezone
            val spainZonedTime = ukZonedTime.withZoneSameInstant(spainZone)

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z")
            
            return ResponseEntity.ok(
                TimezoneConversionResponse(
                    ukTime = ukZonedTime.format(formatter),
                    spainTime = spainZonedTime.format(formatter),
                    timeDifference = "Spain is ${calculateTimeDifference(ukZonedTime, spainZonedTime)} hour(s) ahead of UK"
                )
            )
        } catch (e: DateTimeParseException) {
            return ResponseEntity.badRequest().build()
        } catch (e: NumberFormatException) {
            return ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/convert")
    fun convertTimezone(@RequestBody request: TimezoneConversionRequest): ResponseEntity<TimezoneConversionResponse> {
        return convertUkToSpain(request.time, request.date)
    }

    private fun parseTime(time: String): java.time.LocalTime {
        return try {
            java.time.LocalTime.parse(time)
        } catch (e: DateTimeParseException) {
            // Try parsing HH:mm format
            try {
                val parts = time.split(":")
                if (parts.size >= 2) {
                    java.time.LocalTime.of(parts[0].toInt(), parts[1].toInt())
                } else {
                    java.time.LocalTime.MIDNIGHT
                }
            } catch (e: NumberFormatException) {
                java.time.LocalTime.MIDNIGHT
            }
        }
    }

    private fun calculateTimeDifference(ukTime: ZonedDateTime, spainTime: ZonedDateTime): Int {
        val ukOffset = ukTime.offset.totalSeconds / 3600
        val spainOffset = spainTime.offset.totalSeconds / 3600
        return spainOffset - ukOffset
    }
}
