package com.moviles.f1app.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.moviles.f1app.data.common.Constantes


@Entity(
    tableName = Constantes.teams,
    indices = [Index(value = [Constantes.name], unique = true)]
)
data class TeamEntity(
    @ColumnInfo(name = Constantes.name)
    val name: String,
    @ColumnInfo(name = Constantes.id)
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = Constantes.car)
    val car: String,
)


