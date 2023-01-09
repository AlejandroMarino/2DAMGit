package com.moviles.f1app.data.repository

import com.moviles.f1app.data.dao.RaceDao
import com.moviles.f1app.data.modelo.toDriverRaceCrossRef
import com.moviles.f1app.data.modelo.toRace
import com.moviles.f1app.data.modelo.toRaceEntity
import com.moviles.f1app.domain.modelo.Race
import javax.inject.Inject

class RaceRepository @Inject constructor(private val raceDao: RaceDao) {

    suspend fun getRaces() = raceDao.getRaces().map { it.toRace() }

    suspend fun getRace(id: Int) = raceDao.getRace(id).toRace()

    suspend fun addRace(race: Race) = raceDao.addRace(race.toRaceEntity())

    suspend fun deleteRace(race: Race) = raceDao.deleteRace(
        race.toRaceEntity(),
        race.performances.toList().map { it.second.toDriverRaceCrossRef() })

}