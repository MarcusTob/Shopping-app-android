package com.example.pgr_208_exam.screens.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgr_208_exam.data.Product
import com.example.pgr_208_exam.data.ProductRepository
import com.example.pgr_208_exam.data.room.Cart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductDetailsViewModel: ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()

    private val _isInCart = MutableStateFlow(false)

    //takes the id of the product clicked, and takes the id and shows it
    fun setSelectedProduct(productId: Int) {
        viewModelScope.launch {
            _loading.value = true
            _selectedProduct.value = ProductRepository.getProductById(productId)
            _isInCart.value = isCurrentProductInCart()
            _loading.value = false
        }
    }

    //updates the cart if the product is not already in cart
    fun updateCart(productId: Int) {
        viewModelScope.launch {
            ProductRepository.insertCart(Cart(productId))
            _isInCart.value = isCurrentProductInCart()
        }
    }
    //checks if the product is in shopping cart
    private suspend fun isCurrentProductInCart(): Boolean {
        return ProductRepository.getCart().any { it.productId == selectedProduct.value?.id }
    }
}