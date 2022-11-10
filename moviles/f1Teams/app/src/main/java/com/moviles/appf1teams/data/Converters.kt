package com.moviles.appf1teams.data

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun intToBoolean(value: Int): Boolean {
        return value == 1
    }

    @TypeConverter
    fun booleanToInt(value: Boolean): Int {
        return if (value) 1 else 0
    }

}