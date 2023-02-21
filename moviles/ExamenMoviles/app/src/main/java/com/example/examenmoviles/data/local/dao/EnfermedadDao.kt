package com.example.examenmoviles.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.examenmoviles.data.modelo.EnfermedadEntity

@Dao
interface EnfermedadDao {

    @Query("SELECT * FROM enfermedades")
    suspend fun getAll(): List<EnfermedadEntity>

    @Query("SELECT * FROM enfermedades WHERE idPaciente = :id")
    suspend fun getEnfermedadesDePaciente(id: Int): List<EnfermedadEntity>
}