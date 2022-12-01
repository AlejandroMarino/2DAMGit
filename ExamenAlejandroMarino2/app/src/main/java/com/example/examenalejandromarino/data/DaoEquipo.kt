package com.example.examenalejandromarino.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.examenalejandromarino.data.modelo.ComponenteEntity
import com.example.examenalejandromarino.data.modelo.EquipoEntity
import com.example.examenalejandromarino.data.modelo.EquipoWithComponente

@Dao
interface DaoEquipo {
    //Ordenar
    @Query("SELECT * FROM equipos")
    suspend fun getEquiposComponente(): List<EquipoWithComponente>

    @Query("SELECT * FROM equipos WHERE id = :id")
    suspend fun getEquipoComponente(id: Int): EquipoWithComponente

    @Query("SELECT * FROM equipos")
    suspend fun getEquipos(): List<EquipoEntity>

    @Query("SELECT * FROM equipos WHERE id = :id")
    suspend fun getEquipo(id: Int): EquipoEntity

    @Insert(onConflict = androidx.room.OnConflictStrategy.ABORT)
    suspend fun addEquipo(equipo: EquipoEntity)

    @Query("DELETE FROM equipos WHERE id = :id ")
    suspend fun deleteEquipo(id : Int)

    @Query("SELECT * FROM componentes WHERE id_equipo = :id")
    suspend fun getComponentes(id: Int): List<ComponenteEntity>

    @Query("SELECT * FROM componentes WHERE nombre = :nombre")
    suspend fun getComponente(nombre: String)

    @Insert(onConflict = androidx.room.OnConflictStrategy.ABORT)
    suspend fun addComponente(componente: ComponenteEntity)

}