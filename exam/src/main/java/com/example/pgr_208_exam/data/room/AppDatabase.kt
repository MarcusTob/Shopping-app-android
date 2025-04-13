package com.example.pgr_208_exam.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pgr_208_exam.data.Product
import com.example.pgr_208_exam.data.ProductListConverter

@Database(
    entities = [Product::class, Cart::class, Order::class],
    version = 6,
    exportSchema = false
)
@TypeConverters(ProductListConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
}