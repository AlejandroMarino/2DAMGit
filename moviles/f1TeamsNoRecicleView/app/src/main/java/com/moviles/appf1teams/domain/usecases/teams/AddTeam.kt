package com.moviles.appf1teams.domain.usecases.teams

import com.moviles.appf1teams.data.Repository
import com.moviles.appf1teams.domain.modelo.Team

class AddTeam {
    operator fun invoke(team: Team): Boolean {
        return if (Repository.getTeam(team.name) == null) {
            Repository.addTeam(team)
            true
        }else{
            false
        }
    }
}