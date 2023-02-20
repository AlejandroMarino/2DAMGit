package com.example.compose2.domain.usecases

import com.example.compose2.data.ProductsRepository
import com.example.compose2.domain.modelo.Product
import javax.inject.Inject

class AddProduct @Inject constructor(private val productsRepository: ProductsRepository) {

    suspend fun invoke(product: Product): Boolean {
        return try {
            productsRepository.addProduct(product)
            true
        } catch (e: Exception) {
            false
        }
    }
}
