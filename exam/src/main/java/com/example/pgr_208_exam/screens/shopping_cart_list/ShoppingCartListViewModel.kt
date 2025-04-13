package com.example.pgr_208_exam.screens.shopping_cart_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pgr_208_exam.data.Product
import com.example.pgr_208_exam.data.ProductRepository
import com.example.pgr_208_exam.data.room.Cart
import com.example.pgr_208_exam.data.room.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class ShoppingCartListViewModel: ViewModel() {

    private val _shoppingCart = MutableStateFlow<List<Product>>(emptyList())
    val shoppingCart = _shoppingCart.asStateFlow()

    //gets shopping cart from database
    fun loadShoppingCart() {
        viewModelScope.launch(Dispatchers.IO) {
            val listOfProductIds = ProductRepository.getCart().map { it.productId }
            _shoppingCart.value = ProductRepository.getProductsByIds(listOfProductIds)
        }
    }
    //handles removing item from cart
    suspend fun removeFromCart(productId: Int) {
        val cartItem = Cart(productId)

        ProductRepository.removeFromCart(cartItem)
        loadShoppingCart()
    }

    //handles buying products and adding them to orderhistory with the current time
    fun onBuyButtonClick() {
        viewModelScope.launch {
            if (shoppingCart.value.isEmpty()) {
                return@launch
            }
            else {
                val cartItems = _shoppingCart.value
                val totalPrice = calculateTotalPrice()
                val dateOfOrder = getDateOfOrder()
                val order = Order(
                    items = cartItems,
                    totalPrice = totalPrice,
                    dateOfOrder = dateOfOrder
                )
                ProductRepository.saveOrder(order)
                ProductRepository.clearCart()
                loadShoppingCart()
            }
        }
    }
    //calculates the total price of shopping cart
    fun calculateTotalPrice(): Double {
        return _shoppingCart.value.sumOf { it.price }
    }
    //handles retrieving the date and time
    fun getDateOfOrder(): String {
        val date = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val dateOfOrder = date.format(Date())
        return dateOfOrder
    }
}
