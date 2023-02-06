package com.example.compose.domain.usecases

import com.example.compose.data.ProductsRepository
import javax.inject.Inject

class GetProducts @Inject constructor(private val productsRepository: ProductsRepository) {

    suspend fun invoke() = productsRepository.getProducts()
}
