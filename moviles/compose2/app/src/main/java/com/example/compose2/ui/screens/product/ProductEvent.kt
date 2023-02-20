package com.example.compose2.ui.screens.product

import com.example.compose2.domain.modelo.Product

sealed class ProductEvent {
    class AddProduct(val product: Product) : ProductEvent()
    class OnValueChanged(val product: Product) : ProductEvent()
}