package com.example.compose2.domain.usecases

import com.example.compose2.data.ProductsRepository
import javax.inject.Inject

class GetProduct @Inject constructor(private val productsRepository: ProductsRepository) {

    suspend fun invoke(id: Int) = productsRepository.getProduct(id)

}