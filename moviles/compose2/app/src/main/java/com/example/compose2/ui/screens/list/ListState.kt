package com.example.compose2.ui.screens.list

import com.example.compose2.domain.modelo.Product

data class ListState(
    val products: List<Product> = emptyList(),
    val message: String = "",
)