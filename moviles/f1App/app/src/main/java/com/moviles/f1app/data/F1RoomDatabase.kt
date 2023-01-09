package com.moviles.f1app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moviles.f1app.data.common.Constantes
import com.moviles.f1app.data.dao.DriverDao
import com.moviles.f1app.data.dao.PerformanceDao
import com.moviles.f1app.data.dao.RaceDao
import com.moviles.f1app.data.dao.TeamDao
import com.moviles.f1app.data.modelo.DriverEntity
import com.moviles.f1app.data.modelo.DriverRaceCrossRef
import com.moviles.f1app.data.modelo.RaceEntity
import com.moviles.f1app.data.modelo.TeamEntity

@Database(
    entities = [RaceEntity::class, DriverEntity::class, TeamEntity::class, DriverRaceCrossRef::class],
    version = Constantes.version,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class F1RoomDatabase : RoomDatabase() {

    abstract fun raceDao(): RaceDao
    abstract fun driverDao(): DriverDao
    abstract fun teamDao(): TeamDao
    abstract fun performanceDao(): PerformanceDao

}