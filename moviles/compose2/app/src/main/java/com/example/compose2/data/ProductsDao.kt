package com.example.compose2.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.compose2.data.modelo.ProductEntity

@Dao
interface ProductsDao {

    @Query("SELECT * FROM products")
    suspend fun getProducts(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getProduct(id: Int): ProductEntity

    @Insert(onConflict = androidx.room.OnConflictStrategy.ABORT)
    suspend fun addProduct(product: ProductEntity)
}