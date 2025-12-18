# Timezone Conversion API

## Overview

This API provides timezone conversion functionality between UK and Spain timezones.

## Question Answered

**"Si algo sale en midnight 19 (mañana) en UK qué hora sería aquí en España?"**

**Answer:** If something is released at midnight (00:00) on the 19th in UK, it would be **01:00 (1 AM)** in Spain.

## Explanation

- UK uses GMT (Greenwich Mean Time) or BST (British Summer Time)
- Spain uses CET (Central European Time) or CEST (Central European Summer Time)
- Spain is typically **1 hour ahead** of the UK

## API Endpoints

### 1. Convert UK Time to Spain Time (GET)

**Endpoint:** `GET /api/timezone/convert-uk-to-spain`

**Parameters:**
- `time` (optional, default: "00:00"): Time in HH:mm format
- `date` (optional): Date in yyyy-MM-dd format (if not provided, uses tomorrow's date)

**Example Request:**
```
GET /api/timezone/convert-uk-to-spain?time=00:00&date=2025-12-19
```

**Example Response:**
```json
{
  "ukTime": "2025-12-19 00:00:00 GMT",
  "spainTime": "2025-12-19 01:00:00 CET",
  "timeDifference": "Spain is 1 hour(s) ahead of UK"
}
```

### 2. Convert Timezone (POST)

**Endpoint:** `POST /api/timezone/convert`

**Request Body:**
```json
{
  "time": "00:00",
  "date": "2025-12-19"
}
```

**Response:**
```json
{
  "ukTime": "2025-12-19 00:00:00 GMT",
  "spainTime": "2025-12-19 01:00:00 CET",
  "timeDifference": "Spain is 1 hour(s) ahead of UK"
}
```

## Testing

The implementation includes comprehensive unit tests in `TimezoneControllerTest.kt` that validate:
- UK midnight to Spain time conversion
- Handling of optional date parameter
- Default midnight time behavior

## Technical Details

- Uses Java's `ZoneId` for timezone handling
  - UK: `Europe/London`
  - Spain: `Europe/Madrid`
- Properly handles daylight saving time transitions
- Returns formatted timestamps with timezone information
