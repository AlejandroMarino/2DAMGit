package com.moviles.appf1teams.domain.usecases.teams

import com.moviles.appf1teams.data.TeamRepository

class Delete(private val teamRepository: TeamRepository) {

    suspend fun invoke(name: String): Boolean {
        return run {
            teamRepository.deleteTeam(name)
            true
        }
    }

//    operator fun invoke(name: String): Boolean {
//        val team = Repository.getTeam(name)
//        return if (team != null) {
//            Repository.deleteTeam(team)
//            true
//        }else{
//            false
//        }
//    }
}