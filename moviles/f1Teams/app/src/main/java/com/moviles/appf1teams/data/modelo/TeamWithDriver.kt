package com.moviles.appf1teams.data.modelo

import androidx.room.Embedded
import androidx.room.Relation
import com.moviles.appf1teams.data.common.Constantes

data class TeamWithDriver(
    @Embedded val team: TeamEntity,
    @Relation(
        parentColumn = Constantes.id,
        entityColumn = Constantes.id_team
    )
    val drivers: List<DriverEntity>?
)