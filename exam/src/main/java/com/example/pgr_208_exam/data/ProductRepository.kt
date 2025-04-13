package com.example.pgr_208_exam.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.pgr_208_exam.data.room.AppDatabase
import com.example.pgr_208_exam.data.room.Cart
import com.example.pgr_208_exam.data.room.Order
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductRepository {
    private val _httpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

    private val _retrofit =
        Retrofit.Builder()
            .client(_httpClient)
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val _productService = _retrofit.create(ProductService::class.java)
    private lateinit var _appDatabase: AppDatabase
    private val _productDao by lazy { _appDatabase.productDao() }
    private val _cartDao by lazy { _appDatabase.cartDao() }
    private val _orderDao by lazy { _appDatabase.orderDao() }

    fun initializeDatabase(context: Context) {
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "product-database"
        ).fallbackToDestructiveMigration().build()

    }
    suspend fun getProducts(): List<Product> {
        try {
            val response = _productService.getAllProducts()

            if(response.isSuccessful) {
                val products = response.body() ?: emptyList()
                _productDao.insertProducts(products)
                return _productDao.getProducts()
            } else {
                throw Exception("Response was not successful")
            }
        } catch (e: Exception) {
            Log.e("productRepository", "Failed to get list of products", e)
            return _productDao.getProducts()
        }
    }

    suspend fun getProductById(productId: Int): Product? {
        return _productDao.getProductById(productId)
    }

    suspend fun getProductsByIds(idList: List<Int>): List<Product> {
        return _productDao.getProductsByIds(idList)
    }

    suspend fun getCart(): List<Cart> {
        return _cartDao.getCart()
    }

    suspend fun insertCart(cart: Cart) {
        _cartDao.insertCart(cart)
    }

    suspend fun removeFromCart(cart: Cart) {
        _cartDao.removeFromCart(cart)
    }
    suspend fun saveOrder(order: Order) {
        _orderDao.insertOrder(order)
    }

    fun getOrderHistory(): Flow<List<Order>> {
        return _orderDao.getAllOrders()
    }
    suspend fun clearOrderHistory() {
        _orderDao.clearOrderHistory()
    }

    suspend fun clearCart() {
        _cartDao.clearCart()
    }

}