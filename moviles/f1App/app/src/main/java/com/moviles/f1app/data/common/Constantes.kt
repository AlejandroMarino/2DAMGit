package com.moviles.f1app.data.common

object Constantes {

    //Queries

    //Team
    const val queryGetAllTeams = "SELECT * FROM teams"
    const val queryGetTeam = "SELECT * FROM teams WHERE id = :id"
    const val queryGetTeamByName = "SELECT * FROM teams WHERE name = :name"
    const val queryUpdateTeam = "UPDATE teams SET name = :name, car = :car WHERE id = :id"
    //Driver
    const val queryGetDrivers = "SELECT * FROM drivers"
    const val queryGetDriversOfTeam = "SELECT * FROM drivers WHERE id_team =:idTeam"
    const val queryGetDriver = "SELECT * FROM drivers WHERE idDriver = :id"
    const val queryGetDriverByName = "SELECT * FROM drivers WHERE name = :name"
    const val queryUpdateDriver = "UPDATE drivers SET name = :name, number = :number, photo = :photo, id_team = :idTeam WHERE idDriver = :id"
    //Race
    const val queryGetRaces = "SELECT * FROM races"
    const val queryGetRace = "SELECT * FROM races WHERE idRace = :id"
    const val queryGetRaceByTrack = "SELECT * FROM races WHERE track = :track"
    const val queryUpdateRace = "UPDATE races SET track = :track, date = :date WHERE idRace = :id"
    //Performance
    const val queryGetPerformance = "SELECT * FROM driver_race WHERE idDriver = :idDriver AND idRace = :idRace"
    const val queryGerDriverPerformances = "SELECT * FROM driver_race WHERE idDriver = :idDriver"
    const val queryGerRacePerformances = "SELECT * FROM driver_race WHERE idRace = :idRace"
    const val queryUpdatePerformance = "UPDATE driver_race SET position = :position, fastest_lap = :fastestLap WHERE idDriver = :idDriver AND idRace = :idRace"


    //Database

    const val database = "item_database"
    const val databaseLocation = "database/efe1.db"
    const val assetdb = "assetDB"
    const val destructiveMigration = 1
    const val version = 2


    //Entity

    const val teams = "teams"
    const val drivers = "drivers"
    const val races = "races"
    const val driver_race = "driver_race"
    const val id = "id"
    const val name = "name"
    const val number = "number"
    const val photo = "photo"
    const val id_team = "id_team"
    const val idDriver = "idDriver"
    const val idRace = "idRace"
    const val position = "position"
    const val fastest_lap = "fastest_lap"
    const val track = "track"
    const val date = "date"
    const val car = "car"
}
