package com.example.pgr_208_exam.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pgr_208_exam.data.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM Product")
    suspend fun getProducts(): List<Product>

    @Query("SELECT * FROM Product WHERE :productId = id")
    suspend fun getProductById(productId: Int): Product?

    @Query("SELECT * FROM Product WHERE id IN (:idList)")
    suspend fun getProductsByIds(idList: List<Int>): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)

}