package com.example.pgr_208_exam.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ShoppingCart")
data class Cart (
    @PrimaryKey
    val productId: Int
)