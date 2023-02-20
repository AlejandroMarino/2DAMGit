package com.example.compose2.ui.screens.product

import com.example.compose2.domain.modelo.Product

data class ProductState(
    val product: Product = Product(),
    val message: String = "",
)