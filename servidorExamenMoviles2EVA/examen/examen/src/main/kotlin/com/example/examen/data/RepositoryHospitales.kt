package com.example.examen.data

import com.example.examen.domain.Enfermedad
import com.example.examen.domain.Hospital
import com.example.examen.domain.Paciente
import com.example.examen.errores.NotFoundException
import org.springframework.stereotype.Repository
import org.springframework.web.context.annotation.ApplicationScope
import java.util.*

@Repository
@ApplicationScope
class RepositoryHospitales {

    private var _hospitales = mutableListOf<Hospital>()


    init {
        _hospitales.add(
            Hospital(
                UUID.randomUUID(), "Hospital de la Paz", 100, "Madrid",
                mutableListOf(
                    Paciente(UUID.randomUUID(), "Paco", "12345678A", mutableListOf(Enfermedad("orzuelo"),
                        Enfermedad("herida abierta en muslo"))),
                    Paciente(
                        UUID.randomUUID(),
                        "Juan Pedro",
                        "12345678A",
                        mutableListOf(Enfermedad("ceguera transitoria"), Enfermedad("neumonia"), Enfermedad("prostata"))
                    ),
                    Paciente(UUID.randomUUID(), "Luis", "12345678A", mutableListOf(Enfermedad("demencia"), Enfermedad("prostata"))),
                )
            )
        )
        // añadir otro hospital a la lista hospitales
        _hospitales.add(
            Hospital(
                UUID.randomUUID(), "Hospital se me acaban las ideas", 10, "Lepe",
                mutableListOf(
                    Paciente(UUID.randomUUID(), "Irene", "123A", mutableListOf(Enfermedad("jodido en general"), Enfermedad("cancer pulmonar"))),
                    Paciente(UUID.randomUUID(), "Juana", "5678A", mutableListOf(Enfermedad("sordo"), Enfermedad("cojo"), Enfermedad("mudo"),Enfermedad( "manco"))),
                    Paciente(UUID.randomUUID(), "Luisa", "13578A", mutableListOf(Enfermedad("sin bazo"), Enfermedad("sin brazos"))),
                )
            )
        )


    }


    fun getHospitales(): List<Hospital> {

        return _hospitales.toList()
    }

    fun deleteHospital(id : UUID) {
        val hospital = _hospitales.find { it.id == id }
        hospital.let { _hospitales.remove(it) }


        if (hospital == null) throw NotFoundException("Hospital no encontrado")
    }


}
