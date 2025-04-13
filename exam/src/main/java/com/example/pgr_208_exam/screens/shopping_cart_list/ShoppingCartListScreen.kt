package com.example.pgr_208_exam.screens.shopping_cart_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.example.pgr_208_exam.screens.common.ProductItem
import kotlinx.coroutines.launch

@Composable
fun ShoppingCartListScreen(
    viewModel: ShoppingCartListViewModel,
    onBackButtonClick: () -> Unit = {},
    onProductClick: (productId: Int) -> Unit = {},
    navigateToOrderHistory: () -> Unit = {},
    onCheckout: MutableState<Boolean>
) {
    val products = viewModel.shoppingCart.collectAsState()
    val totalPrice = viewModel.calculateTotalPrice()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onBackButtonClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Navigate Back"
                )
            }
            Text(
                text = "Shopping Cart",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.15.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
            )
            IconButton(
                onClick = { navigateToOrderHistory() }) {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Order History"
                )
            }
        }
        Row {
            //styling for 2nd header, shows price and "buy items in cart" button
            //also displays "go to order history" after user has bought items in cart
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Total Price: $$totalPrice",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Row {
                    //button to purchase items in cart
                    //if cart is empty, it does nothing
                    //if cart has products, displays a snackbar with a button
                    //to navigate to the order history screen
                    Button(
                        onClick = {
                            if (products.value.isEmpty()){
                                return@Button
                            }else {
                                viewModel.onBuyButtonClick()
                                onCheckout.value = true
                            }
                        }, modifier = Modifier
                            .padding(8.dp),
                        shape = RectangleShape,
                        colors = ButtonDefaults
                            .buttonColors( Color.DarkGray
                            )
                    ) {
                        Text(text = "Buy Items in Cart")
                        }
                    }
                //styling for snackbar
                if (onCheckout.value) {
                    Snackbar(action = {
                        ElevatedButton(
                            onClick = {
                                navigateToOrderHistory();
                            }, modifier = Modifier
                                .padding(8.dp)
                        ) {
                            Text("See Order History")

                        }
                    }) {
                        Text("Items bought!")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Divider()
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            //shows all items in cart, with a delete button to remove the item from the cart
            items(products.value) { product ->
                ProductItem(product = product,
                    onClick = {
                        onProductClick(product.id)
                    })
                ElevatedButton(
                    onClick = {
                        viewModel.viewModelScope.launch {
                            viewModel.removeFromCart(product.id)
                        }
                    }, modifier = Modifier
                        .padding(start = 8.dp)
                        .fillMaxWidth(1f),
                    colors = ButtonDefaults
                        .buttonColors( Color.Red )
                ) {
                    Text(
                        text = "Remove from Cart",
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        if(products.value.isEmpty()){
            //checks if cart is empty, if true, displays message
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Cart is empty! Navigate to the main screen to add items to your cart!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
        }
    }
}
