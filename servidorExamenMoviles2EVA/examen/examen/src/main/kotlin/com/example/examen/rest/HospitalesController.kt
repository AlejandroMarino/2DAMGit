package com.example.examen.rest

import com.example.examen.data.RepositoryHospitales
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/hospitales")
class HospitalesController(val repositoryHospitales: RepositoryHospitales) {


    @GetMapping("")
    fun getHospitales() = repositoryHospitales.getHospitales()


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteHospital(@PathVariable id: String) {
        repositoryHospitales.deleteHospital(UUID.fromString(id))
    }
}







