package com.moviles.appf1teams.domain.usecases.teams

import com.moviles.appf1teams.data.TeamRepository
import com.moviles.appf1teams.domain.modelo.Team

class AddTeam(private val teamRepository: TeamRepository) {

    suspend fun invoke(team: Team): Boolean {
        return run {
            teamRepository.addTeam(team)
            true
        }
    }

//    operator fun invoke(team: Team): Boolean {
//        return if (Repository.getTeam(team.name) == null) {
//            Repository.addTeam(team)
//            true
//        }else{
//            false
//        }
//    }
}