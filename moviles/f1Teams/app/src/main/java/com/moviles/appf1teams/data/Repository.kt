package com.moviles.appf1teams.data

import com.moviles.appf1teams.domain.modelo.Team


//BORRAR

object Repository {
    private val teams = mutableListOf<Team>()

    init {
//        teams.add(Team("Mercedes", 80F,2,true))
//        teams.add(Team("Alpine",60F,3,false))
//        teams.add(Team("Red Bull",100F,1,true))
//        teams.add(Team("Mclaren",70F,2,false))
//        teams.add(Team("Ferrari",90F,3,true))
    }
    fun addTeam(team: Team) = teams.add(team)

    fun getTeams(): List<Team> {
        return teams
    }

    fun getTeam(name: String): Team? {
        return teams.find { it.name == name }
    }

    fun deleteTeam(team: Team) = teams.remove(team)


    fun updateTeam(team: Team, name: String, performance: Float, tyre: Int, winner: Boolean){
        team.name = name
        team.performance = performance
        team.tyre = tyre
        team.winner = winner
    }

}