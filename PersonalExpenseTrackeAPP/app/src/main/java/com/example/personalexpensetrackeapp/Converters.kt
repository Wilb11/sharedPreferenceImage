package com.example.personalexpensetrackeapp.utils

import androidx.room.TypeConverter
import java.util.Date
import java.util.*

class Converters {
    @TypeConverter
    fun fromDate(date: Date): Long = date.time
    @TypeConverter
    fun toDate(timestamp: Long): Date = Date(timestamp)
}
