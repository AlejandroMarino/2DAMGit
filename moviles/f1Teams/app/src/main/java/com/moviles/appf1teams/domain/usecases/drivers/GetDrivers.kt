package com.moviles.appf1teams.domain.usecases.drivers

import com.moviles.appf1teams.data.TeamRepository
import javax.inject.Inject

class GetDrivers @Inject constructor(private val teamRepository: TeamRepository){
    suspend fun invoke(id: Int) = teamRepository.getDrivers(id)
}