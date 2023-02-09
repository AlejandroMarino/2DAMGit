package com.example.compose.ui.screens.product

import com.example.compose.domain.modelo.Product

data class ProductState(
    val product: Product = Product(),
    val message: String = "",
)