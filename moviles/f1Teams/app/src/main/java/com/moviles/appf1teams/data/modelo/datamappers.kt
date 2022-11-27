package com.moviles.appf1teams.data.modelo

import com.moviles.appf1teams.domain.modelo.Driver
import com.moviles.appf1teams.domain.modelo.Team

fun TeamEntity.toTeam(): Team {
    return Team(
        this.id, this.name, this.performance, this.tyre, this.winner,
    )
}

fun Team.toTeamEntity(): TeamEntity {
    return TeamEntity(
        this.name, this.id, this.performance, this.winner, this.tyre,
    )
}

fun DriverEntity.toDriver(): Driver {
    return Driver(
        this.id, this.name, this.number
    )
}

fun Driver.toDriverEntity(teamId: Int): DriverEntity {
    return DriverEntity(this.id, this.name, this.number, teamId)
}

fun TeamWithDriver.toTeam(): Team {
    return Team(
        this.team.id, this.team.name, this.team.performance, this.team.tyre, this.team.winner, this.drivers?.map { it.toDriver() }
    )
}

fun Team.toTeamWithDriver(): TeamWithDriver {
    return TeamWithDriver(
        this.toTeamEntity(), this.drivers?.map { it.toDriverEntity(this.id) }
    )
}
