package com.moviles.f1app.data.modelo

import androidx.room.*
import com.moviles.f1app.data.common.Constantes

@Entity(
    tableName = Constantes.drivers,
    indices = [Index(value = [Constantes.name], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = TeamEntity::class,
            parentColumns = [Constantes.id],
            childColumns = [Constantes.id_team]
        )
    ]
)
data class DriverEntity(
    @ColumnInfo(name = Constantes.idDriver)
    @PrimaryKey(autoGenerate = true)
    val idDriver: Int = 0,
    @ColumnInfo(name = Constantes.name)
    val name: String,
    @ColumnInfo(name = Constantes.number)
    val number: Int,
    @ColumnInfo(name = Constantes.id_team)
    val id_team: Int = 0,
)