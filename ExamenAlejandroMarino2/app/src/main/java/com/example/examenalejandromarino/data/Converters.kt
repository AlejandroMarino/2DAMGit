package com.example.examenalejandromarino.data

import androidx.room.TypeConverter
import com.example.examenalejandromarino.domain.modelo.Tipo

class Converters {

    @TypeConverter
    fun tipoToString(value: Tipo): String {
        return value.toString()
    }

    @TypeConverter
    fun stringToTipo(value: String): Tipo {
        return if (value == "entrenador"){
            Tipo.entrenador
        }else{
            Tipo.jugador
        }
    }
}