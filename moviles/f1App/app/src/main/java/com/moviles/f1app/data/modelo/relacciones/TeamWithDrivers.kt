package com.moviles.f1app.data.modelo.relacciones

import androidx.room.Embedded
import androidx.room.Relation
import com.moviles.f1app.data.common.Constantes
import com.moviles.f1app.data.modelo.DriverEntity
import com.moviles.f1app.data.modelo.TeamEntity

data class TeamWithDrivers(
    @Embedded val team: TeamEntity,
    @Relation(
        parentColumn = Constantes.id,
        entityColumn = Constantes.id_team
    )
    val drivers: List<DriverEntity>
)