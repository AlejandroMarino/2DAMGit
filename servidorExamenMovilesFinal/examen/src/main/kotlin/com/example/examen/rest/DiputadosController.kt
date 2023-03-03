package com.example.examen.rest

import com.example.examen.data.RepositoryDiputados
import com.example.examen.domain.Diputado
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/diputados")
class DiputadosController(val repositoryDiputados: RepositoryDiputados) {


    @GetMapping("")
    fun getDiputado() = repositoryDiputados.getDiputados()

    @GetMapping("/{idPatido}")
    fun getDiputadosPartido(@PathVariable idPatido: String) = repositoryDiputados.getDiputadosPartido(UUID.fromString(idPatido))


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteDiputado(@PathVariable id: String) {
        repositoryDiputados.deleteDiputado(UUID.fromString(id))
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun addDiputado(@RequestBody diputado: Diputado) {
        repositoryDiputados.addDiputado(diputado)
    }
}







