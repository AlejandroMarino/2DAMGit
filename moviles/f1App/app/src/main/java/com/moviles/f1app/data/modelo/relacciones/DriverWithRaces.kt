package com.moviles.f1app.data.modelo.relacciones

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.moviles.f1app.data.common.Constantes
import com.moviles.f1app.data.modelo.DriverEntity
import com.moviles.f1app.data.modelo.DriverRaceCrossRef
import com.moviles.f1app.data.modelo.RaceEntity

class DriverWithRaces(
    @Embedded
    val driver: DriverEntity,
    @Relation(
        parentColumn = Constantes.idDriver,
        entityColumn = Constantes.idDriver,
        associateBy = Junction(DriverRaceCrossRef::class)
    )
    val races: List<DriverRaceCrossRef>
)