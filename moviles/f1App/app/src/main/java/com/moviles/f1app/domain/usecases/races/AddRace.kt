package com.moviles.f1app.domain.usecases.races

import com.moviles.f1app.data.repository.RaceRepository
import com.moviles.f1app.domain.modelo.Race
import javax.inject.Inject

class AddRace @Inject constructor(private val raceRepository: RaceRepository) {

    suspend fun invoke(race: Race):Boolean{
        return try {
            raceRepository.addRace(race)
            true
        }catch (e:Exception){
            false
        }
    }
}