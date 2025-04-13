package com.example.pgr_208_exam.screens.product_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pgr_208_exam.screens.common.LoadingScreen
import com.example.pgr_208_exam.screens.common.ProductItem

@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel,
    onProductClick: (productId: Int) -> Unit = {},
    navigateToShoppingCartList: () -> Unit = {},
    navigateToOrderHistoryScreen: () -> Unit = {}

) {
    val loading = viewModel.loading.collectAsState()
    val products = viewModel.products.collectAsState()

    if (loading.value) {
        LoadingScreen()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Products",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.15.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { viewModel.loadProducts() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh products",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(
                    onClick = { navigateToShoppingCartList() }) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Shopping cart",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                IconButton(
                    onClick = { navigateToOrderHistoryScreen() }) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Order history",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
        //filter categories
        Column {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.DarkGray)
            ){
                Button(
                    modifier = Modifier
                        .height(50.dp),
                    onClick = {
                        viewModel.filter("men's clothing")},
                    shape = RectangleShape,
                    colors = ButtonDefaults
                        .buttonColors( Color.DarkGray
                    )
                ) {
                    Text("Men's")
                }
                Button(
                    modifier = Modifier
                        .height(50.dp),
                    onClick = {
                    viewModel.filter("women's clothing")},
                    shape = RectangleShape,
                    colors = ButtonDefaults
                        .buttonColors( Color.DarkGray
                        )
                ) {
                    Text(text = "Women's")
                }
                Button(
                    modifier = Modifier
                        .height(50.dp),
                    onClick = {
                    viewModel.filter("jewelery")},
                    shape = RectangleShape,
                    colors = ButtonDefaults
                        .buttonColors( Color.DarkGray
                        )
                ) {
                    Text(text = "Jewelery")
                }
                Button(
                    modifier = Modifier
                        .height(50.dp),
                    onClick = {
                    viewModel.filter("electronics")},
                    shape = RectangleShape,
                    colors = ButtonDefaults
                        .buttonColors( Color.DarkGray
                        )
                ) {
                    Text(
                        text = "Tech",
                        overflow = TextOverflow.Visible
                    )
                }
            }
        }
        //list of products, uses ProductItem.kt to render products
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Black)
        ) {
            items(products.value) { product ->
                ProductItem(product = product, onClick = {
                    onProductClick(product.id)
                })
            }
        }
    }
}