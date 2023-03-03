package com.example.examenfinalmoviles.data.local

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class Converters {
    private var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    @TypeConverter
    fun fromTimestamp(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it, formatter) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String? {
        return date?.format(formatter)
    }
}
