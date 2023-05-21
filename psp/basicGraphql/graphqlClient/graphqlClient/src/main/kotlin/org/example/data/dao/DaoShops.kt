package org.example.data.dao

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.javafx.JavaFx
import org.example.data.apollo.BaseGraphqlResponse
import org.example.domain.model.Shop
import org.example.utils.Trither
import org.example.warehouse.*

object DaoShops : BaseGraphqlResponse() {
    fun getAllShops(): Flow<Trither<List<Shop>>> {
        return flow {
            emit(value = Trither.Loading())
            val result = safeApiCall(
                apiCall = { apolloClient.query(GetAllShopsQuery()).execute() },
                transform = { data -> data.getAllShops.map { Shop(id = it?.id ?: 0, name = it?.name ?: "") } }
            )
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }

    fun getShopsFilterByName(name: String): Flow<Trither<List<Shop>>> {
        return flow {
            emit(value = Trither.Loading())
            val result = safeApiCall(
                apiCall = { apolloClient.query(ShopsFilterByNameQuery(name)).execute() },
                transform = { data -> data.shopsFilterByName.map { Shop(id = it?.id ?: 0, name = it?.name ?: "") } }
            )
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }

    fun getShop(id: Int): Flow<Trither<Shop>> {
        return flow {
            emit(value = Trither.Loading())
            val result = safeApiCall(
                apiCall = { apolloClient.query(GetShopQuery(id)).execute() },
                transform = { data -> data.getShop.let { Shop(id = it?.id ?: 0, name = it?.name ?: "") } }
            )
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }

    fun addShop(name: String): Flow<Trither<Int>> {
        return flow {
            emit(value = Trither.Loading())
            val result = safeApiCall(
                apiCall = { apolloClient.mutation(AddShopMutation(name)).execute() },
                transform = { data -> data.addShop.id }
            )
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }

    fun updateShop(shop: Shop): Flow<Trither<Shop>> {
        return flow {
            emit(value = Trither.Loading())
            val result = safeApiCall(
                apiCall = {
                    apolloClient.mutation(UpdateShopMutation(shop.id, shop.name)).execute()
                },
                transform = { data -> data.updateShop.let { Shop(id = it.id, name = it.name) } }
            )
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }

    fun deleteShop(id: Int): Flow<Trither<Boolean>> {
        return flow {
            emit(value = Trither.Loading())
            val result = safeApiCall(
                apiCall = { apolloClient.mutation(DeleteShopMutation(id)).execute() },
                transform = { data -> data.deleteShop }
            )
            emit(result)
        }.flowOn(Dispatchers.JavaFx)
    }

}