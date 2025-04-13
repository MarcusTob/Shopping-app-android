package com.example.pgr_208_exam.screens.product_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgr_208_exam.data.Product
import com.example.pgr_208_exam.data.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    //creates a separate list of products for the filtering
    private val _productsToBeFiltered = MutableStateFlow<List<Product>>(emptyList())

    init {
        loadProducts()
    }

    //sets loading screen while products load
    //gets products from ProductRepository
    fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            _products.value = ProductRepository.getProducts()
            _loading.value = false
        }
    }

    //filters list based on category given as parameter
    fun filter(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _productsToBeFiltered.value = ProductRepository.getProducts()
            val filteredProducts = _productsToBeFiltered.value.filter { it.category == category }
            _products.value = filteredProducts
        }
    }
}