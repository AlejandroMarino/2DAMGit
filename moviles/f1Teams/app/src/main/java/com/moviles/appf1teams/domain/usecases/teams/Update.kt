package com.moviles.appf1teams.domain.usecases.teams

import com.moviles.appf1teams.data.Repository

class Update {
    operator fun invoke(
        name: String,
        newName: String,
        performance: Float,
        tyre: Int,
        winner: Boolean
    ){
        val team = Repository.getTeam(name)
        if(team!=null)
            Repository.updateTeam(team, newName, performance, tyre, winner)
    }
}