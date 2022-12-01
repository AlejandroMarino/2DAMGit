package com.example.examenalejandromarino.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.examenalejandromarino.data.modelo.EquipoEntity
import com.example.examenalejandromarino.data.modelo.EquipoWithComponente

@Dao
interface DaoEquipo {
    //Ordenar
    @Query("SELECT * FROM equipos")
    suspend fun getEquipos(): List<EquipoWithComponente>

    @Query("SELECT * FROM equipos WHERE id = :id")
    suspend fun getEquipo(id: Int): EquipoWithComponente

    @Insert(onConflict = androidx.room.OnConflictStrategy.ABORT)
    suspend fun addEquipo(equipo: EquipoEntity)

    @Query("DELETE FROM equipos WHERE id = :id ")
    suspend fun deleteEquipo(id : Int)
}