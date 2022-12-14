package com.moviles.f1app.data.common

object Constantes {

    //Queries

    const val querieGetAllTeams = "SELECT * FROM teams"
    const val querieGetTeam = "SELECT * FROM teams WHERE id = :id"
    const val querieGetTeamByName = "SELECT * FROM teams WHERE name = :name"
    const val querieUpdateTeam = "UPDATE teams SET name = :name, car = :car WHERE id = :id"
    const val querieGetDrivers = "SELECT * FROM drivers"
    const val querieGetDriversOfTeam = "SELECT * FROM drivers WHERE id_team =:idTeam"
    const val querieGetDriver = "SELECT * FROM drivers WHERE idDriver = :id"
    const val querieGetDriverByName = "SELECT * FROM drivers WHERE name = :name"
    const val querieUpdateDriver = "UPDATE drivers SET name = :name, number = :number, id_team = :idTeam WHERE idDriver = :id"
    const val querieGetDriverTeam = "SELECT id_team From drivers WHERE idDriver = :id"
    const val querieDeleteDriver = "DELETE FROM drivers WHERE idDriver = :idDriver"
    const val querieGetRaces = "SELECT * FROM races"
    const val querieGetRace = "SELECT * FROM races WHERE idRace = :id"
    const val querieGetRaceByTrack = "SELECT * FROM races WHERE track = :track"
    const val querieUpdateRace = "UPDATE races SET track = :track, date = :date WHERE idRace = :id"
    const val querieGetPerformance = "SELECT * FROM driver_race WHERE idDriver = :idDriver AND idRace = :idRace"
    const val querieGerDriverPerformances = "SELECT * FROM driver_race WHERE idDriver = :idDriver"
    const val querieGerRacePerformances = "SELECT * FROM driver_race WHERE idRace = :idRace"
    const val querieUpdatePerformance = "UPDATE driver_race SET position = :position, fastest_lap = :fastestLap WHERE idDriver = :idDriver AND idRace = :idRace"


    //Database

    const val database = "item_database"
    const val databaseLocation = "database/efe1.db"
    const val assetdb = "assetDB"
    const val destructiveMigration = 1
    const val version = 3


    //Entity

    const val teams = "teams"
    const val drivers = "drivers"
    const val races = "races"
    const val driver_race = "driver_race"
    const val id = "id"
    const val name = "name"
    const val number = "number"
    const val id_team = "id_team"
    const val idDriver = "idDriver"
    const val idRace = "idRace"
    const val position = "position"
    const val fastest_lap = "fastest_lap"
    const val track = "track"
    const val date = "date"
    const val car = "car"
}
