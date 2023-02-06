package com.example.compose.data.modelo

import com.example.compose.domain.modelo.Product

fun ProductEntity.toProduct() = Product(
    id = id,
    name = name,
    price = price,
    description = description,
)

fun Product.toProductEntity() = ProductEntity(
    id = id,
    name = name,
    price = price,
    description = description,
)