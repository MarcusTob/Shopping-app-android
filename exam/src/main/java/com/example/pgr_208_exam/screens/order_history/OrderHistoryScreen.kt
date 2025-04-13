package com.example.pgr_208_exam.screens.order_history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pgr_208_exam.data.room.Order
import com.example.pgr_208_exam.screens.common.ProductItem


@Composable
fun OrderHistoryScreen(
    viewModel: OrderHistoryViewModel,
    onBackButtonClick: () -> Unit = {},
    navigateToProductList: () -> Unit = {}
) {
    val orderHistory by viewModel.getOrderHistory().collectAsState(initial = emptyList())

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBackButtonClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "Navigate Back"
                )
            }
            Text(
                text = "Order History",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.15.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            IconButton(
                onClick = { navigateToProductList() }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Product List"
                )
            }
        }
        //prints out each order in the order history, with a divider between
        //as an attempt to separate them properly, we also added a background to orderItem
        //which you can see further down
        LazyColumn {
            items(orderHistory) { order ->
                OrderItem(order = order)
                Divider()
            }
        }
        if (orderHistory.isEmpty()) {
            Text(
            modifier = Modifier.padding(8.dp),
            text = "You have no previous orders!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            )
        }
    }
}

//styling for each order
@Composable
fun OrderItem(order: Order) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.Gray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier
            .height(8.dp))
        Text(
            text = "Total Price: $${order.totalPrice}",
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Order placed on: ${order.dateOfOrder}",
            fontWeight = FontWeight.SemiBold
        )
        order.items.forEach { product ->
            ProductItem(product = product)
        }
    }
}