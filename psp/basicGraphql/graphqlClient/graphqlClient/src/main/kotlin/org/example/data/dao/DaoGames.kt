package org.example.data.dao

import com.apollographql.apollo3.api.Optional
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.javafx.JavaFx
import org.example.data.apollo.BaseGraphqlResponse
import org.example.domain.model.Game
import org.example.domain.model.Shop
import org.example.utils.Trither
import org.example.warehouse.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DaoGames : BaseGraphqlResponse() {

    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun getAllGames(): Flow<Trither<List<Game>>> {
        return flow {
            emit(value = Trither.Loading())
            val result = safeApiCall(
                apiCall = { apolloClient.query(GetAllGamesQuery()).execute() },
                transform = { data ->
                    data.getAllGames.map {
                        Game(
                            id = it?.id ?: 0,
                            name = it?.name ?: "",
                            description = it?.description,
                            releaseDate = it?.releaseDate.let { date -> if (date != null) LocalDate.parse(date) else null },
                            shop = Shop(id = it?.shop?.id ?: 0, name = it?.shop?.name ?: "")
                        )
                    }
                }
            )
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }

    fun getGamesFilterByName(name: String): Flow<Trither<List<Game>>> {
        return flow {
            emit(value = Trither.Loading())
            val result = safeApiCall(
                apiCall = { apolloClient.query(GamesFilterByNameQuery(name)).execute() },
                transform = { data ->
                    data.gamesFilterByName.map {
                        Game(
                            id = it?.id ?: 0,
                            name = it?.name ?: "",
                            description = it?.description,
                            releaseDate = it?.releaseDate.let { date -> if (date != null) LocalDate.parse(date) else null },
                            shop = Shop(id = it?.shop?.id ?: 0, name = it?.shop?.name ?: "")
                        )
                    }
                }
            )
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }

    fun getGamesOfShop(id: Int): Flow<Trither<List<Game>>> {
        return flow {
            emit(value = Trither.Loading())
            val result = safeApiCall(
                apiCall = { apolloClient.query(GetShopQuery(id)).execute() },
                transform = { data ->
                    data.getShop?.let { shop ->
                        val s = Shop(
                            id = shop.id,
                            name = shop.name
                        )
                        shop.games?.map {
                            Game(
                                id = it?.id ?: 0,
                                name = it?.name ?: "",
                                description = it?.description,
                                releaseDate = it?.releaseDate.let { date -> if (date != null) LocalDate.parse(date) else null },
                                shop = s
                            )
                        }
                    } ?: emptyList()
                }
            )
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }

    fun getGame(id: Int): Flow<Trither<Game>> {
        return flow {
            emit(value = Trither.Loading())
            val result = safeApiCall(
                apiCall = { apolloClient.query(GetGameQuery(id)).execute() },
                transform = { data ->
                    data.getGame.let {
                        Game(
                            id = it?.id ?: 0,
                            name = it?.name ?: "",
                            description = it?.description,
                            releaseDate = it?.releaseDate.let { date -> if (date != null) LocalDate.parse(date) else null },
                            shop = Shop(id = it?.shop?.id ?: 0, name = it?.shop?.name ?: "")
                        )
                    }
                }
            )
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }

    fun addGame(game: Game): Flow<Trither<Int>> {
        return flow {
            emit(value = Trither.Loading())
            val result = safeApiCall(
                apiCall = {
                    val des : Optional<String?> = Optional.presentIfNotNull(game.description)
                    val date : Optional<String?> = Optional.presentIfNotNull(game.releaseDate?.toString())
                    apolloClient.mutation(
                        AddGameMutation(
                            game.name,
                            des,
                            date,
                            game.shop.id
                        )
                    ).execute()
                },
                transform = { data ->
                    data.addGame.id
                }
            )
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }

    fun updateGame(game: Game): Flow<Trither<Game>> {
        return flow {
            emit(value = Trither.Loading())
            val result = safeApiCall(
                apiCall = {
                    val des : Optional<String?> = Optional.presentIfNotNull(game.description)
                    val date : Optional<String?> = Optional.presentIfNotNull(game.releaseDate?.toString())
                    apolloClient.mutation(
                        UpdateGameMutation(
                            game.id,
                            game.name,
                            des,
                            date,
                            game.shop.id
                        )
                    ).execute()
                },
                transform = { data ->
                    data.updateGame.let {
                        Game(
                            id = it.id,
                            name = it.name,
                            description = it.description,
                            releaseDate = it.releaseDate.let { date -> if (date != null) LocalDate.parse(date) else null },
                            shop = Shop(id = it.shop.id, name = it.shop.name)
                        )
                    }
                }
            )
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }

    fun deleteGame(id: Int): Flow<Trither<Boolean>> {
        return flow {
            emit(value = Trither.Loading())
            val result = safeApiCall(
                apiCall = {
                    apolloClient.mutation(
                        DeleteGameMutation(id)
                    ).execute()
                },
                transform = { data ->
                    data.deleteGame
                }
            )
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }

}