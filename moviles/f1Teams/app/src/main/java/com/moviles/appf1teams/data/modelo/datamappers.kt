package com.moviles.appf1teams.data.modelo

import com.moviles.appf1teams.domain.modelo.Team

fun TeamEntity.toTeam(): Team {
    return Team(
        this.name, this.performance, this.tyre, this.winner
    )
}

fun Team.toTeamEntity(): TeamEntity {
    return TeamEntity(
        this.name, this.performance, this.tyre, this.winner
    )
}