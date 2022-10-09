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
        val team = Repository.getInstance().getTeam(name)
        if(team!=null)
            Repository.getInstance().updateTeam(team, newName, performance, tyre, winner)
    }
}