package com.moviles.f1app.data

import androidx.room.TypeConverter
import java.time.LocalDate


class Converters {

    @TypeConverter
    fun fromTimestamp(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }


    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String? {
        return date?.toString()
    }
}
