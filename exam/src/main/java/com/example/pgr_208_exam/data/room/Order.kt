package com.example.pgr_208_exam.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.pgr_208_exam.data.Product
import com.example.pgr_208_exam.data.ProductListConverter

@Entity(tableName = "Order")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @TypeConverters(ProductListConverter::class)
    val items: List<Product>,
    val totalPrice: Double,
    val dateOfOrder: String
)