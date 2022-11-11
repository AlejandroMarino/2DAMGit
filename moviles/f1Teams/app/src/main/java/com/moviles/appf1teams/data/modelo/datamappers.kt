package com.moviles.appf1teams.data.modelo

import com.moviles.appf1teams.domain.modelo.Team

fun TeamEntity.toTeam(): Team {
    return Team(
        this.id, this.name, this.performance, this.tyre, this.winner
    )
}

fun Team.toTeamEntity(): TeamEntity {
    return TeamEntity(
       this.id, this.name, this.performance, this.tyre, this.winner
    )
}