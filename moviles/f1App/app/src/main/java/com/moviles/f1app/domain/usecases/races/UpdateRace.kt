package com.moviles.f1app.domain.usecases.races

import com.moviles.f1app.data.repository.RaceRepository
import com.moviles.f1app.domain.modelo.Race
import javax.inject.Inject

class UpdateRace @Inject constructor(private val raceRepository: RaceRepository) {
    suspend fun invoke(race: Race): Boolean {
        return try {
            raceRepository.updateRace(race.id, race.track, race.date)
            true
        } catch (e: Exception) {
            false
        }
    }
}