package com.moviles.appf1teams.domain.usecases.drivers

import com.moviles.appf1teams.data.TeamRepository
import com.moviles.appf1teams.domain.modelo.Driver
import javax.inject.Inject

class AddDriver @Inject constructor(private val teamRepository: TeamRepository) {

    suspend fun invoke(id: Int, driver: Driver): Boolean {
        return try {
            teamRepository.addDriver(id, driver)
            true
        } catch (e: Exception) {
            false
        }
    }

}