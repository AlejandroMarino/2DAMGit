package com.moviles.appf1teams.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.moviles.appf1teams.data.common.Constantes


@Entity(tableName = Constantes.tableName,
indices = [Index(value = [Constantes.name], unique = true)])
data class TeamEntity(
    @ColumnInfo(name = Constantes.name)
    val name: String,
    @ColumnInfo(name = Constantes.id)
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = Constantes.performance)
    val performance: Float,
    @ColumnInfo(name = Constantes.winner)
    val winner: Boolean,
    @ColumnInfo(name = Constantes.tyre)
    val tyre: Int,
)


