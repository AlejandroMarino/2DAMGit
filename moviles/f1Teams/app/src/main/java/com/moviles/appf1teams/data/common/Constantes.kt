package com.moviles.appf1teams.data.common

object Constantes {

    //Queries

    const val querieGetAllTeams = "SELECT * FROM teams"
    const val querieGetTeam = "SELECT * FROM teams WHERE id = :id"
    const val querieDeleteTeam = "DELETE FROM teams WHERE id = :id"
    const val querieUpdateTeam = "UPDATE teams SET name = :name, performance = :performance, tyre = :tyre, winner = :winner WHERE id = :id"
    const val querieGetDrivers = "SELECT * FROM drivers WHERE id_team =:id"
    const val querieDeleteDriver = "DELETE FROM drivers WHERE id = :idDriver"


    //Database

    const val database = "item_database"
    const val databaseLocation = "database/teams.db"
    const val assetdb = "assetDB"
    const val destructiveMigration = 1


    //Entity

    const val tableName = "teams"
    const val id = "id"
    const val name = "name"
    const val performance = "performance"
    const val tyre = "tyre"
    const val winner = "winner"
    const val drivers = "drivers"
    const val id_team = "id_team"

}
