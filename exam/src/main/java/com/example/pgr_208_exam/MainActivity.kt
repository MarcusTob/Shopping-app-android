package com.example.pgr_208_exam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pgr_208_exam.data.ProductRepository
import com.example.pgr_208_exam.screens.order_history.OrderHistoryScreen
import com.example.pgr_208_exam.screens.order_history.OrderHistoryViewModel
import com.example.pgr_208_exam.screens.product_details.ProductDetailsScreen
import com.example.pgr_208_exam.screens.product_details.ProductDetailsViewModel
import com.example.pgr_208_exam.screens.product_list.ProductListScreen
import com.example.pgr_208_exam.screens.product_list.ProductListViewModel
import com.example.pgr_208_exam.screens.shopping_cart_list.ShoppingCartListScreen
import com.example.pgr_208_exam.screens.shopping_cart_list.ShoppingCartListViewModel
import com.example.pgr_208_exam.ui.theme.PGR_208_EXAMTheme

class MainActivity : ComponentActivity() {

    private val _productListViewModel: ProductListViewModel by viewModels()
    private val _productDetailsViewModel: ProductDetailsViewModel by viewModels()
    private val _shoppingCartListViewModel: ShoppingCartListViewModel by viewModels()
    private val _orderHistoryViewModel: OrderHistoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Starts database when app is started
        ProductRepository.initializeDatabase(applicationContext)

        setContent {
            PGR_208_EXAMTheme {
                //navController to handle navigation logic
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "productListScreen"
                ) {
                    composable(
                        route = "productListScreen"
                    ) {
                        ProductListScreen(
                            viewModel = _productListViewModel,
                            onProductClick = { productId ->
                                navController.navigate("productDetailsScreen/$productId")
                            },
                            navigateToShoppingCartList = {
                                navController.navigate("shoppingCartListScreen")
                            },
                            navigateToOrderHistoryScreen = {
                                navController.navigate("orderHistoryScreen")
                            }
                        )
                    }
                    composable(
                        route = "productDetailsScreen/{productId}",
                        arguments = listOf(navArgument(name = "productId") {
                            type = NavType.IntType
                        })
                    ) { backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId") ?: -1
                        LaunchedEffect(productId) {
                            _productDetailsViewModel.setSelectedProduct(productId)
                        }
                        ProductDetailsScreen(
                            viewModel = _productDetailsViewModel,
                            onBackButtonClick = { navController.popBackStack() },
                            navigateToShoppingCartList = {
                                navController.navigate("shoppingCartListScreen")
                            },
                        )
                    }

                    composable(
                        route = "shoppingCartListScreen"
                    ) {
                        LaunchedEffect(Unit) {
                            _shoppingCartListViewModel.loadShoppingCart()
                        }

                        ShoppingCartListScreen(viewModel = _shoppingCartListViewModel,
                            onBackButtonClick = { navController.popBackStack() },
                            onProductClick = { productId ->
                                navController.navigate("productDetailsScreen/$productId")
                            },
                            onCheckout = remember { mutableStateOf(false) },
                            navigateToOrderHistory = { navController.navigate("orderHistoryScreen") })
                    }
                    composable(
                        route = "orderHistoryScreen"
                    ) {
                        OrderHistoryScreen(viewModel = _orderHistoryViewModel,
                            onBackButtonClick = { navController.popBackStack() },
                            navigateToProductList = {
                                navController.navigate("productListScreen")

                            })
                    }
                }
            }
        }
    }
}