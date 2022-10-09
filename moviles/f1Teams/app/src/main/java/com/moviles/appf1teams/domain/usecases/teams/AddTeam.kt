package com.moviles.appf1teams.domain.usecases.teams

import com.moviles.appf1teams.data.Repository
import com.moviles.appf1teams.domain.modelo.Team

class AddTeam {
    operator fun invoke(team: Team): Boolean {
        return if (Repository.getInstance().getTeam(team.name) == null) {
            Repository.getInstance().addTeam(team)
            true;
        }else{
            false;
        }
    }
}