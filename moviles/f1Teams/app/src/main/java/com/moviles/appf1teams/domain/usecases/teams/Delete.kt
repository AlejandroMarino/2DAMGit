package com.moviles.appf1teams.domain.usecases.teams

import com.moviles.appf1teams.data.TeamRepository

class Delete(private val teamRepository: TeamRepository) {

    suspend fun invoke(id: Int): Boolean {
        return try{
            teamRepository.deleteTeam(id)
            true
        }catch (e: Exception){
            false
        }
    }
}