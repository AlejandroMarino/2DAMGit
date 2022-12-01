package com.example.examenalejandromarino.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.examenalejandromarino.data.modelo.ComponenteEntity

@Dao
interface DaoComponente {
    @Query("SELECT * FROM componentes WHERE id_equipo = :id")
    suspend fun getComponentes(id: Int): List<ComponenteEntity>

    @Query("SELECT * FROM componentes WHERE nombre = :nombre")
    suspend fun getComponente(nombre: String)

    @Insert(onConflict = androidx.room.OnConflictStrategy.ABORT)
    suspend fun addComponente(componente: ComponenteEntity)
}