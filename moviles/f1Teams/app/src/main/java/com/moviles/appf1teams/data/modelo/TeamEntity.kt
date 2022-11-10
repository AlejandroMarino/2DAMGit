package com.moviles.appf1teams.data.modelo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "teams")
data class TeamEntity(

    @ColumnInfo(name = "name")
    @PrimaryKey(autoGenerate = false)
    val name: String,
    @ColumnInfo(name = "performance")
    val performance: Float,
    @ColumnInfo(name = "tyre")
    val tyre: Int,
    @ColumnInfo(name = "winner")
    val winner: Boolean,
)


