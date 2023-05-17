package org.example.ui

import com.apollographql.apollo3.ApolloClient
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {

    val apolloClient = ApolloClient.Builder()
        .serverUrl("http://localhost:8080/warehouse")
        .build()

    runBlocking {
//        apolloClient.query().execute().data?.let {
//            println(it.characters?.results?.map { it?.name })
//        }
    }


}