package com.example.compose.data

import com.example.compose.data.modelo.toProduct
import com.example.compose.data.modelo.toProductEntity
import com.example.compose.domain.modelo.Product
import javax.inject.Inject

class ProductsRepository @Inject constructor(private val productsDao: ProductsDao) {

    suspend fun getProducts() = productsDao.getProducts().map { it.toProduct() }

    suspend fun getProduct(id: Int) = productsDao.getProduct(id).toProduct()

    suspend fun addProduct(product: Product) = productsDao.addProduct(product.toProductEntity())
}
