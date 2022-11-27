package com.moviles.appf1teams.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moviles.appf1teams.data.modelo.DriverEntity
import com.moviles.appf1teams.data.modelo.TeamEntity

@Database(entities = [TeamEntity::class, DriverEntity::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class TeamsRoomDatabase : RoomDatabase() {

    abstract fun teamDao(): TeamDao
}
