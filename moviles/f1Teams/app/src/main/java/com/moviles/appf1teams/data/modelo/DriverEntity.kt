package com.moviles.appf1teams.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.moviles.appf1teams.data.common.Constantes

@Entity(
    tableName = "drivers",
    indices = [Index(value = [Constantes.name], unique = true)],
    foreignKeys = [
        ForeignKey(entity = TeamEntity::class,
            parentColumns = ["id"],
            childColumns = ["id_team"])
            ])
data class DriverEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "number")
    val number: Int,
    @ColumnInfo(name = "id_team")
    val id_team: Int = 0,
)