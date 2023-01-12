package com.moviles.f1app.data.modelo

import com.moviles.f1app.data.modelo.relacciones.DriverWithRaces
import com.moviles.f1app.data.modelo.relacciones.RaceWithDrivers
import com.moviles.f1app.data.modelo.relacciones.TeamWithDrivers
import com.moviles.f1app.domain.modelo.*


fun TeamEntity.toTeam(): Team {
    return Team(
        this.id, this.name, this.car,
    )
}

fun Team.toTeamEntity(): TeamEntity {
    return TeamEntity(
        this.name, this.id, this.car,
    )
}

fun TeamWithDrivers.toTeam(): Team {
    return Team(
        this.team.id, this.team.name, this.team.car, this.drivers.map { it.toDriver() }
    )
}

fun Team.toTeamWithDriver(): TeamWithDrivers {
    return TeamWithDrivers(
        this.toTeamEntity(), this.drivers.map { it.toDriverEntity() }
    )
}

fun DriverEntity.toDriver(): Driver {
    return Driver(
        this.idDriver, this.name, this.number, idTeam = this.id_team
    )
}

fun Driver.toDriverEntity(): DriverEntity {
    return DriverEntity(this.id, this.name, this.number, this.idTeam)
}

fun DriverWithRaces.toDriver(): Driver {
    val map: Map<Int, Performance> = this.races.associateBy({ it.idRace }, { it.toPerformance() })
    return Driver(
        this.driver.idDriver, this.driver.name, this.driver.number, map, this.driver.id_team
    )
}

fun Driver.toDriverWithRaces(): DriverWithRaces {
    return DriverWithRaces(
        this.toDriverEntity(),
        this.performances.toList().map { it.second.toDriverRaceCrossRef() }
    )
}

fun Race.toRaceEntity(): RaceEntity {
    return RaceEntity(this.id, this.track, this.date)
}

fun RaceEntity.toRace(): Race {
    return Race(this.idRace, this.track, this.date)
}

fun RaceWithDrivers.toRace(): Race {
    val map: Map<Int, Performance> =
        this.drivers.associateBy({ it.idDriver }, { it.toPerformance() })
    return Race(
        this.race.idRace, this.race.track, this.race.date, map
    )
}

fun Race.toRaceWithDrivers(): RaceWithDrivers {
    return RaceWithDrivers(
        this.toRaceEntity(),
        this.performances.toList().map { it.second.toDriverRaceCrossRef() }
    )
}

fun Performance.toDriverRaceCrossRef(): DriverRaceCrossRef {
    return DriverRaceCrossRef(this.idDriver, this.idRace, this.position, this.fastestLap)
}

fun DriverRaceCrossRef.toPerformance(): Performance {
    return Performance(this.idDriver, this.idRace, this.position, this.fastestLap)
}

fun DriverRaceWithNames.toPerformance(): PerformanceWithObjects {
    return PerformanceWithObjects(this.driver.toDriver(), this.race.toRace(), this.position, this.fastestLap)
}