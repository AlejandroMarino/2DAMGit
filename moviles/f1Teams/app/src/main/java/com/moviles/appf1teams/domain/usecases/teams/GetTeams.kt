package com.moviles.appf1teams.domain.usecases.teams

import com.moviles.appf1teams.data.TeamRepository
import javax.inject.Inject

class GetTeams @Inject constructor(private val teamRepository: TeamRepository) {
    suspend fun invoke() = teamRepository.getTeams()

}