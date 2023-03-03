package com.example.examenfinalmoviles.ui.screens.partidos

import com.example.examenfinalmoviles.domain.modelo.Partido

sealed class PartidosEvent {
    object ErrorCatch: PartidosEvent()
    object GetPartidos : PartidosEvent()
    class GetPartido(val partido: Partido) : PartidosEvent()
    class AddPartido(val nombre: String) : PartidosEvent()
    class UpdatePartido(val nombre: String) : PartidosEvent()
    class DeletePartido(val partido: Partido) : PartidosEvent()
}