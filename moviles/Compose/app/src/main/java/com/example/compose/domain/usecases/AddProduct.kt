package com.example.compose.domain.usecases

import com.example.compose.data.ProductsRepository
import com.example.compose.domain.modelo.Product
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
