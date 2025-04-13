package com.example.pgr_208_exam.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ProductListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String): List<Product> {
        val listType = object : TypeToken<List<Product>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Product>): String {
        return gson.toJson(list)
    }
}
