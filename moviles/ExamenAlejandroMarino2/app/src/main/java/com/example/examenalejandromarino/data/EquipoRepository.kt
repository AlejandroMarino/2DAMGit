package com.example.examenalejandromarino.data

import com.example.examenalejandromarino.data.modelo.toComponenteEntity
import com.example.examenalejandromarino.data.modelo.toEquipo
import com.example.examenalejandromarino.data.modelo.toEquipoEntity
import com.example.examenalejandromarino.domain.modelo.Componente
import com.example.examenalejandromarino.domain.modelo.Equipo
import javax.inject.Inject

class EquipoRepository @Inject constructor(
    private val daoEquipo: DaoEquipo,
) {

    suspend fun getEquipos() = daoEquipo.getEquipos().map { it.toEquipo() }

    suspend fun getEquipo(id: Int) = daoEquipo.getEquipo(id).toEquipo()

    suspend fun deleteEquipo(id: Int) = daoEquipo.deleteEquipo(id)

    suspend fun addEquipo(equipo: Equipo) = daoEquipo.addEquipo(equipo.toEquipoEntity())

    suspend fun addComponente(id: Int, componente: Componente) = daoEquipo.addComponente(componente.toComponenteEntity(id))

    suspend fun getComponentes(id: Int) = daoEquipo.getComponentes(id)

    suspend fun getComponente(nombre: String) = daoEquipo.getComponente(nombre)

}