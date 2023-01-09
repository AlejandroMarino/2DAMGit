package com.moviles.f1app.domain.usecases.races

import com.moviles.f1app.data.repository.RaceRepository
import javax.inject.Inject

class DeleteRace @Inject constructor(private val raceRepository: RaceRepository) {
    suspend fun invoke(id: Int): Boolean {
        val race = raceRepository.getRace(id)
        return try {
            raceRepository.deleteRace(race)
            true
        } catch (e: Exception) {
            false
        }
    }
}