package org.example.domain.model

data class Shop(
    val id: Int,
    val name: String,
    val games: List<Game>? = null,


) {
    override fun toString(): String {
        return name
    }
}