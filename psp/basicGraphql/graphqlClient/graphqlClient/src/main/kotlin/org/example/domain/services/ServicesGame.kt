package org.example.domain.services

import org.example.data.dao.DaoGames
import org.example.domain.model.Game

object ServicesGame {
    private val dG : DaoGames = DaoGames

    fun getAllGames() = dG.getAllGames()
    fun getGamesFilterByName(name: String) = dG.getGamesFilterByName(name)
    fun getGamesOfShop(id: Int) = dG.getGamesOfShop(id)
    fun getGame(id: Int) = dG.getGame(id)
    fun addGame(game: Game) = dG.addGame(game)
    fun updateGame(game: Game) = dG.updateGame(game)
    fun deleteGame(id: Int) = dG.deleteGame(id)
}