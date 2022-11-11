package com.moviles.appf1teams.domain.usecases.teams

import com.moviles.appf1teams.data.TeamRepository

class Update(private val teamRepository: TeamRepository) {

    suspend fun invoke(
        id: Int?,
        name: String,
        performance: Float,
        tyre: Int,
        winner: Boolean
    ) = id?.let { teamRepository.updateTeam(it, name, performance, tyre, winner) }

//    operator fun invoke(
//        name: String,
//        newName: String,
//        performance: Float,
//        tyre: Int,
//        winner: Boolean
//    ){
//        val team = Repository.getTeam(name)
//        if(team!=null)
//            Repository.updateTeam(team, newName, performance, tyre, winner)
//    }
}