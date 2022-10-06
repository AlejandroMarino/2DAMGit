package com.moviles.appf1teams.data

import com.moviles.appf1teams.domain.modelo.Team

class Repository {
    fun addTeams(team: Team) = teams.add(team)

    fun getTeams(): List<Team> {
        return teams
    }

    init {
        teams.add(Team("Mercedes","England","light-blue", 80))
        teams.add(Team("Alpine","France","blue", 60))
        teams.add(Team("Red BUll","Nederland","black", 100))
        teams.add(Team("Mclaren","England","orange", 70))
        teams.add(Team("Ferrari","Italy","red", 90))
    }

    companion object {
        private val teams = mutableListOf<Team>()
        fun getInstance(): Repository = Repository()
    }
}