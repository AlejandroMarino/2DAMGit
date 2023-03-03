package com.example.examenfinalmoviles.ui.screens.diputados

import com.example.examenfinalmoviles.domain.modelo.Partido

sealed class DiputadosEvent {
    object ErrorCatch : DiputadosEvent()
    object GetPartidos : DiputadosEvent()
    class GetDiputados(val partido: Partido) : DiputadosEvent()
}