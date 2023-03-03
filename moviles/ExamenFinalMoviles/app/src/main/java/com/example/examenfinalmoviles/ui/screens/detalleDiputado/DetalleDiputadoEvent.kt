package com.example.examenfinalmoviles.ui.screens.detalleDiputado

import java.util.*

sealed class DetalleDiputadoEvent {
    class GetDiputado(val id: UUID) : DetalleDiputadoEvent()
    object ErrorCatch : DetalleDiputadoEvent()
}