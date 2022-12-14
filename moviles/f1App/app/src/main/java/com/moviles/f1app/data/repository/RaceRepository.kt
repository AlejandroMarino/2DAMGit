package com.moviles.f1app.data.repository

import com.moviles.f1app.data.dao.RaceDao
import com.moviles.f1app.data.modelo.toDriverRaceCrossRef
import com.moviles.f1app.data.modelo.toRace
import com.moviles.f1app.data.modelo.toRaceEntity
import com.moviles.f1app.domain.modelo.Race
import java.time.LocalDate
import javax.inject.Inject

class RaceRepository @Inject constructor(private val raceDao: RaceDao) {

    suspend fun getRaces() = raceDao.getRaces().map { it.toRace() }

    suspend fun getRace(id: Int) = raceDao.getRace(id).toRace()

    suspend fun getRaceByTrack(track: String) = raceDao.getRaceByTrack(track).toRace()

    suspend fun addRace(race: Race) = raceDao.addRace(race.toRaceEntity())

    suspend fun updateRace(id: Int, track: String, date: LocalDate) = raceDao.updateRace(id, track, date)

    suspend fun deleteRace(race: Race) = raceDao.deleteRace(
        race.toRaceEntity(),
        race.performances.toList().map { it.second.toDriverRaceCrossRef() })

}