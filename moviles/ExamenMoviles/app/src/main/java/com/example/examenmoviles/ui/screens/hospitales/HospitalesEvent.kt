package com.example.examenmoviles.ui.screens.hospitales

import com.example.examenmoviles.domain.modelo.Hospital

sealed class HospitalesEvent {
    object GetHospitales : HospitalesEvent()
    class GetPacientes(val hospital: Hospital) : HospitalesEvent()

    class DeleteHospital(val hospital: Hospital) : HospitalesEvent()
}