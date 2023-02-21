package com.example.examen.rest

import com.example.examen.data.RepositoryPacientes
import com.example.examen.domain.Enfermedad
import com.example.examen.domain.Paciente
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pacientes")
class PacientesController (val repositoryPacientes: RepositoryPacientes) {

    @GetMapping("")
    fun getPacientes() = repositoryPacientes.getPacientes()

    @PutMapping("/{id}")
    fun updatePacientes(@PathVariable id:String,@RequestBody paciente: Paciente)
        = repositoryPacientes.updatePacientes(id,paciente)

    @PostMapping("/{id}")
    fun addEnfermedadPacientes(@PathVariable id:String,@RequestBody enfermedad: Enfermedad)
        = repositoryPacientes.addEnfermedadPacientes(id,enfermedad)

}







