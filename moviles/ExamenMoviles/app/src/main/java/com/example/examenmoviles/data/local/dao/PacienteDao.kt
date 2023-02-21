package com.example.examenmoviles.data.local.dao

import androidx.room.*
import com.example.examenmoviles.data.modelo.PacienteEntity
import com.example.examenmoviles.data.modelo.relaciones.PacienteConEnfermedades

@Dao
interface PacienteDao {

    @Query("SELECT * FROM pacientes")
    suspend fun getAll(): List<PacienteConEnfermedades>

    @Query("SELECT * FROM pacientes WHERE id = :id")
    suspend fun getPaciente(id: Int): PacienteConEnfermedades

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pacientes: List<PacienteEntity>)

    @Delete
    suspend fun deleteAll(pacientes: List<PacienteEntity>)
}
