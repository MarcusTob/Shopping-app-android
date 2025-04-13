package com.example.pgr_208_exam.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {
    @Query("SELECT * FROM ShoppingCart")
    suspend fun getCart(): List<Cart>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: Cart)

    @Delete
    suspend fun removeFromCart(cart: Cart)

    @Query("DELETE FROM ShoppingCart")
    suspend fun clearCart()

}