package org.example.utils

sealed class Trither<T>(
    var data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T, message: String? = null) : Trither<T>(data, message)

    class Error<T>(message: String, data: T? = null) : Trither<T>(data, message)

    class Loading<T> : Trither<T>()

}