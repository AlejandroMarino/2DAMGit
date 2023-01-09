package com.moviles.f1app.domain.usecases.races

import com.moviles.f1app.data.repository.RaceRepository
import javax.inject.Inject

class GetRaces @Inject constructor(private val raceRepository: RaceRepository) {
    suspend fun invoke() = raceRepository.getRaces()
}