package com.example.compose.ui.screens.list

import com.example.compose.domain.modelo.Product

data class ListState(
    val products: List<Product> = emptyList(),
    val message: String = "",
)