package com.example.pgr_208_exam.screens.order_history
import androidx.lifecycle.ViewModel
import com.example.pgr_208_exam.data.room.Order
import com.example.pgr_208_exam.data.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//retrieves the orders from database, also calculates the orders total price
class OrderHistoryViewModel : ViewModel() {
    fun getOrderHistory(): Flow<List<Order>> {
        return ProductRepository.getOrderHistory().map { orders ->
            orders.map { order ->
                val totalPrice = order.items.sumOf { it.price }
                order.copy(totalPrice = totalPrice)
            }
        }
    }
}