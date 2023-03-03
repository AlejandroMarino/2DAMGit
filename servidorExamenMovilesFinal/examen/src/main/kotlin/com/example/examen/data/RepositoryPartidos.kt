package com.example.examen.data

import com.example.examen.domain.Partido
import org.springframework.stereotype.Repository
import org.springframework.web.context.annotation.ApplicationScope
import java.util.*


@Repository
@ApplicationScope
class RepositoryPartidos() {

    private val _partidos = mutableListOf<Partido>()

    init {
        _partidos.add(Partido(UUID.randomUUID(),"Podemos"))
        _partidos.add(Partido(UUID.randomUUID(),"PP"))
        _partidos.add(Partido(UUID.randomUUID(),"PSOE"))
        _partidos.add(Partido(UUID.randomUUID(),"VOX"))
        _partidos.add(Partido(UUID.randomUUID(),"ERC"))
        _partidos.add(Partido(UUID.randomUUID(),"CIU"))
        _partidos.add(Partido(UUID.randomUUID(),"CIUDADANOS"))
    }

    fun getPartidos() = _partidos.toList()

    fun deletePartido(id: UUID) {
        _partidos.removeIf { it.id == id }
    }

    fun addPartido(partido: Partido) {
        _partidos.add(partido)
    }

    fun updatePartido(partido: Partido) {
        val partidoToUpdate = _partidos.find { it.id == partido.id }
        partidoToUpdate?.nombre = partido.nombre
    }


}


