package org.example.data.apollo

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import org.example.utils.Trither

abstract class BaseGraphqlResponse {
    val apolloClient: ApolloClient = ApolloClient.Builder()
        .serverUrl("http://localhost:8080/graphql")
        .build()
    suspend fun <T : Operation.Data, R> safeApiCall(
        apiCall: suspend () -> ApolloResponse<T>,
        transform: (T) -> R
    ): Trither<R> {
        try {

            val response = apiCall()
            if (!response.hasErrors()) {
                val body = response.data
                body?.let {
                    return Trither.Success(transform(body))
                }
            }
            return error(response.errors?.get(0)?.message ?: "ERROR DESCONOCIDO")
        } catch (e: Exception) {
            e.printStackTrace()
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): Trither<T> =
        Trither.Error(errorMessage)
}
