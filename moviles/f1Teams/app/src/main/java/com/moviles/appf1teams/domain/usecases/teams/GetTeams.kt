package com.moviles.appf1teams.domain.usecases.teams

import com.moviles.appf1teams.data.Repository

class GetTeams {
    operator fun invoke() = Repository.getInstance().getTeams()
}