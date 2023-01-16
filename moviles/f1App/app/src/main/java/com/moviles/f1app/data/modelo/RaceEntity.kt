package com.moviles.f1app.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.moviles.f1app.data.common.Constantes
import java.time.LocalDate

@Entity(
    tableName = Constantes.races,
    indices = [Index(value = [Constantes.track], unique = true)]
)
data class RaceEntity(
    @ColumnInfo(name = Constantes.date)
    val date: LocalDate,
    @ColumnInfo(name = Constantes.idRace)
    @PrimaryKey(autoGenerate = true)
    val idRace: Int = 0,
    @ColumnInfo(name = Constantes.track)
    val track: String,
)