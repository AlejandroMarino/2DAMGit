package com.example.compose.ui.screens.product

import com.example.compose.domain.modelo.Product

sealed class ProductEvent {
    class AddProduct(val product: Product) : ProductEvent()
}