package com.moviles.appf1teams.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.moviles.appf1teams.data.common.Constantes
import com.moviles.appf1teams.data.modelo.TeamEntity

@Database(entities = [TeamEntity::class], version = 5, exportSchema = true)
@TypeConverters(Converters::class)
abstract class TeamsRoomDatabase : RoomDatabase() {

    abstract fun teamDao(): TeamDao

    companion object {
        @Volatile
        private var INSTANCE: TeamsRoomDatabase? = null

        fun getDatabase(context: Context): TeamsRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TeamsRoomDatabase::class.java,
                    Constantes.database
                )
                    .createFromAsset(Constantes.databaseLocation)
                    .fallbackToDestructiveMigrationFrom(1)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
