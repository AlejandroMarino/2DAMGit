package com.example.compose.domain.usecases

import com.example.compose.data.ProductsRepository
import javax.inject.Inject

class GetProduct @Inject constructor(private val productsRepository: ProductsRepository) {

    suspend fun invoke(id: Int) = productsRepository.getProduct(id)

}