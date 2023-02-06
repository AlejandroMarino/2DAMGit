package com.example.compose.domain.modelo

data class Product(
    val id: Int = 0,
    val name: String,
    val price: Double,
    val description: String,
)