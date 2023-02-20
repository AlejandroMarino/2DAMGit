package com.example.compose2.domain.usecases

import com.example.compose2.data.ProductsRepository
import javax.inject.Inject

class GetProducts @Inject constructor(private val productsRepository: ProductsRepository) {

    suspend fun invoke() = productsRepository.getProducts()
}
