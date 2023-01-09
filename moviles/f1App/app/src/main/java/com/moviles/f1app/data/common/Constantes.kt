package com.moviles.f1app.data.common

object Constantes {

    //Queries

    const val querieGetAllTeams = "SELECT * FROM teams"
    const val querieGetTeam = "SELECT * FROM teams WHERE id = :id"
    const val querieDeleteTeam = "DELETE FROM teams WHERE id = :id"
    const val querieUpdateTeam = "UPDATE teams SET name = :name, performance = :performance, tyre = :tyre, winner = :winner WHERE id = :id"
    const val querieGetDrivers = "SELECT * FROM drivers"
    const val querieGetDriversOfTeam = "SELECT * FROM drivers WHERE id_team =:idTeam"
    const val querieGetDriver = "SELECT * FROM drivers WHERE idDriver = :id"
    const val querieGetDriverTeam = "SELECT id_team From drivers WHERE idDriver = :id"
    const val querieDeleteDriver = "DELETE FROM drivers WHERE idDriver = :idDriver"
    const val querieGetRaces = "SELECT * FROM races"
    const val querieGetRace = "SELECT * FROM races WHERE idRace = :id"
    const val querieGetPerformance = "SELECT * FROM driver_race WHERE idDriver = :idDriver AND idRace = :idRace"


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
