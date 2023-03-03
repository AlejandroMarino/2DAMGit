package com.example.examen.rest

import com.example.examen.data.RepositoryDiputados
import com.example.examen.data.RepositoryPartidos
import com.example.examen.domain.Partido
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/partidos")
class PartidosController (val repositoryPacientes: RepositoryPartidos,val repositoryDiputados: RepositoryDiputados) {

    @GetMapping("")
    fun getPacientes() = repositoryPacientes.getPartidos()


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePartido(@PathVariable id: String) {
        repositoryDiputados.deleteDiputadosDePartido(UUID.fromString(id))
        repositoryPacientes.deletePartido(UUID.fromString(id))
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun addPartido(@RequestBody partido: Partido) {
        repositoryPacientes.addPartido(partido)
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updatePartido(@RequestBody partido: Partido) {
        repositoryPacientes.updatePartido(partido)
    }


}







