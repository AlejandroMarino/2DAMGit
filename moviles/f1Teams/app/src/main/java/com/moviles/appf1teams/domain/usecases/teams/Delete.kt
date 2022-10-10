package com.moviles.appf1teams.domain.usecases.teams

import com.moviles.appf1teams.data.Repository

class Delete {
    operator fun invoke(name: String): Boolean {
        val team = Repository.getTeam(name)
        return if (team != null) {
            Repository.deleteTeam(team)
            true;
        }else{
            false;
        }
    }
}