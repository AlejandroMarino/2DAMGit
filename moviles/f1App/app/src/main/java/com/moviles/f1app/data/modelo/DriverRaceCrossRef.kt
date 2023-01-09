package com.moviles.f1app.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.moviles.f1app.data.common.Constantes

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = DriverEntity::class,
            parentColumns = [Constantes.idDriver],
            childColumns = [Constantes.idDriver],

        ),
        ForeignKey(
            entity = RaceEntity::class,
            parentColumns = [Constantes.idRace],
            childColumns = [Constantes.idRace],
        )
    ],
    primaryKeys = [Constantes.idDriver, Constantes.idRace],
    tableName = Constantes.driver_race
)
data class DriverRaceCrossRef(
    val idDriver: Int,
    val idRace: Int,
    @ColumnInfo(name = Constantes.position)
    val position: Int,
    @ColumnInfo(name = Constantes.fastest_lap)
    val fastestLap: String,
)