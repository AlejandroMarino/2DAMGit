package com.example.examenfinalmoviles.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.examenfinalmoviles.data.modelo.PartidoEntity
import java.util.UUID

@Dao
interface PartidoDao {

    @Query("SELECT * FROM partidos")
    suspend fun getAll(): List<PartidoEntity>

    @Query("SELECT * FROM partidos WHERE id = :id")
    suspend fun getPartido(id: UUID): PartidoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPartido(partido: PartidoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(partidos: List<PartidoEntity>)

    @Query("UPDATE partidos SET nombre = :nombre where id = :id")
    suspend fun updatePartido(id: UUID, nombre: String)

    @Delete
    suspend fun deletePartido(partido: PartidoEntity)

    @Delete
    suspend fun deleteAll(partidos: List<PartidoEntity>)

}