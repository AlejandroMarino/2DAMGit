package com.moviles.f1app.data.modelo.relacciones

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.moviles.f1app.data.common.Constantes
import com.moviles.f1app.data.modelo.DriverEntity
import com.moviles.f1app.data.modelo.DriverRaceCrossRef
import com.moviles.f1app.data.modelo.RaceEntity

class RaceWithDrivers(
    @Embedded
    val race: RaceEntity,
    @Relation(
        parentColumn = Constantes.idRace,
        entityColumn = Constantes.idRace,
        associateBy = Junction(DriverRaceCrossRef::class)
    )
    val drivers: List<DriverRaceCrossRef>
)