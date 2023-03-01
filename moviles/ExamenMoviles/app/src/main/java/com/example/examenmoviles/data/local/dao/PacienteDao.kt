package com.example.examenmoviles.data.local.dao

import androidx.room.*
import com.example.examenmoviles.data.modelo.EnfermedadEntity
import com.example.examenmoviles.data.modelo.PacienteEntity
import com.example.examenmoviles.data.modelo.relaciones.PacienteConEnfermedades
import java.util.UUID

@Dao
interface PacienteDao {

    @Query("SELECT * FROM pacientes")
    suspend fun getAll(): List<PacienteConEnfermedades>

    @Query("SELECT * FROM pacientes WHERE id = :id")
    suspend fun getPaciente(id: Int): PacienteConEnfermedades

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pacientes: List<PacienteEntity>)

    @Query("UPDATE pacientes SET nombre = :nombre, dni = :dni where id = :id")
    suspend fun update(id: UUID, nombre: String, dni: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEnfermedad(enfermedad: EnfermedadEntity)

    @Delete
    suspend fun deleteAll(pacientes: List<PacienteEntity>)
}
