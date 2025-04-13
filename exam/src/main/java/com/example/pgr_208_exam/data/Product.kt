package com.example.pgr_208_exam.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey
    val id: Int,
    val title: String,
    val price: Double,
    val category: String,
    val description: String,
    val image: String
)