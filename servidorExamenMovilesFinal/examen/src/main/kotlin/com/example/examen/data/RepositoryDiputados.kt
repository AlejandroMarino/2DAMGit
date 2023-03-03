package com.example.examen.data

import com.example.examen.domain.Diputado
import com.example.examen.errores.NotFoundException
import org.springframework.stereotype.Repository
import org.springframework.web.context.annotation.ApplicationScope
import java.time.LocalDate
import java.util.*

@Repository
@ApplicationScope
class RepositoryDiputados(final val repositoryPartidos: RepositoryPartidos) {

    private var _diputados = mutableListOf<Diputado>()


    init {
        _diputados.add(
            Diputado(
                UUID.randomUUID(), "jose", true, repositoryPartidos.getPartidos()[0].id, LocalDate.now(),
                mutableListOf("A", "B", "C"), mutableListOf("D", "E", "F")
                )
            )
        _diputados.add(
            Diputado(
                UUID.randomUUID(), "pedro", true, repositoryPartidos.getPartidos()[0].id, LocalDate.now(),
                mutableListOf("A", "B", "C"), mutableListOf("D", "E", "F")
            )
        )
        _diputados.add(
            Diputado(
                UUID.randomUUID(), "sanote", false, repositoryPartidos.getPartidos()[0].id, LocalDate.now(),
            )
        )
        _diputados.add(
            Diputado(
                UUID.randomUUID(), "juan jose", true, repositoryPartidos.getPartidos()[1].id, LocalDate.now(),
                mutableListOf("A", "B", "C"), mutableListOf("D", "E", "F")
            )
        )
        _diputados.add(
            Diputado(
                UUID.randomUUID(), "jose luis ", true, repositoryPartidos.getPartidos()[1].id, LocalDate.now(),
                mutableListOf("A", "B", "C"), mutableListOf("D", "E", "F")
            )
        )
        _diputados.add(
            Diputado(
                UUID.randomUUID(), "juan", true, repositoryPartidos.getPartidos()[2].id, LocalDate.now(),
                mutableListOf("A", "B", "C"), mutableListOf("D", "E", "F")
            )
        )
        _diputados.add(
            Diputado(
                UUID.randomUUID(), "jose juan", true, repositoryPartidos.getPartidos()[3].id, LocalDate.now(),
                mutableListOf("A", "B", "C"), mutableListOf("D", "E", "F")
            )
        )
        _diputados.add(
            Diputado(
                UUID.randomUUID(), "pedro J", false, repositoryPartidos.getPartidos()[3].id, LocalDate.now(),

            )
        )



    }


    fun getDiputados(): List<Diputado> {

        return _diputados.toList()
    }

    fun deleteDiputadosDePartido(id : UUID) {
        val diputadosdePartido = _diputados.filter { it.idPartido == id }.toList()
        _diputados.removeAll(diputadosdePartido)


    }

    fun deleteDiputado(id : UUID) {
        val diputado = _diputados.find { it.id == id }
        diputado.let { _diputados.remove(it) }


        if (diputado == null) throw NotFoundException("diputado no encontrado")
    }

    fun getDiputadosPartido(idPartido: UUID) : List<Diputado> {
        val listado = _diputados.filter { it.idPartido == idPartido }.toList()

        if(listado.isEmpty()) throw NotFoundException("diputados no encontrados")

        return listado
    }
    fun addDiputado(diputado: Diputado) {

        repositoryPartidos.getPartidos().find { it.id == diputado.idPartido }?.let {
            _diputados.add(diputado)
        } ?: throw NotFoundException("partido no encontrado")

    }


}
