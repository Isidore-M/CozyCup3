package com.example.cozycup.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cozycup.data.getMenuItemById
import com.example.cozycup.ui.CartViewModel
import com.example.cozycup.ui.ui.theme.AppBackground
import com.example.cozycup.ui.ui.theme.DarkGreen
import com.example.cozycup.ui.ui.theme.TextGray

// Brand Red used for selection and quantity buttons
val BrandRed = Color(0xFFE53935)

@Composable
fun ProductDetailScreen(
    itemId: Int,
    viewModel: CartViewModel,
    onBackClick: () -> Unit
) {
    // 1. Find the specific product data using the ID
    val item = getMenuItemById(itemId) ?: return

    // UI States
    var selectedSize by remember { mutableStateOf("Medium") }
    var quantity by remember { mutableStateOf(1) }
    var showDialog by remember { mutableStateOf(false) }

    // 2. Dynamic Price Calculation Logic
    val basePrice = item.price.filter { it.isDigit() }.toDoubleOrNull() ?: 0.0
    val sizeSurcharge = when (selectedSize) {
        "Small" -> 0.0
        "Medium" -> 5.0
        "Big" -> 10.0
        else -> 0.0
    }
    val totalPrice = (basePrice + sizeSurcharge) * quantity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(AppBackground)
    ) {
        // --- Header Image Banner ---
        Box(modifier = Modifier.fillMaxWidth().height(380.dp)) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Floating Back Button
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(top = 48.dp, start = 20.dp)
                    .size(40.dp)
                    .background(Color.White, CircleShape)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.Black)
            }
        }

        // --- Details Content Area ---
        Column(modifier = Modifier.padding(24.dp)) {

            // Product Title and Dynamic Price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.title,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "${totalPrice.toInt()} CAD",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen
                )
            }

            // Short Ingredients/Description Subtitle
            Text(
                text = item.description,
                fontSize = 16.sp,
                color = TextGray,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- Size and Quantity Selection Section ---
            Text(
                text = "Size and quantity",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Size Choice Chips (Using BrandRed for Selection)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    listOf("Small", "Medium", "Big").forEach { size ->
                        val isSelected = selectedSize == size
                        Surface(
                            modifier = Modifier.clickable { selectedSize = size },
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) DarkGreen else Color.White,
                            shadowElevation = 2.dp
                        ) {
                            Text(
                                text = size,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                color = if (isSelected) Color.White else Color.Black,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                // Quantity Counter with BrandRed Circles
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFFF5F5F5))
                ) {
                    IconButton(onClick = { if (quantity > 1) quantity-- }) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Decrease",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp).background(TextGray, CircleShape)
                        )
                    }
                    Text(
                        text = "$quantity",
                        modifier = Modifier.padding(horizontal = 12.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    IconButton(onClick = { quantity++ }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increase",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp).background(DarkGreen, CircleShape)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // --- Detailed Product Description (Long Description) ---
            Text(
                text = "Description",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.longDescription, // Fetched dynamically from Models.kt
                fontSize = 14.sp,
                color = TextGray,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(64.dp))

            // --- Order Button (Saves to ViewModel) ---
            Button(
                onClick = {
                    viewModel.addToCart(item, selectedSize, quantity, totalPrice)
                    showDialog = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(56.dp)
            ) {
                Text(
                    text = "Order now",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    // --- Success Confirmation Dialog ---
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Continue", color = DarkGreen, fontWeight = FontWeight.Bold)
                }
            },
            title = { Text("Success", fontWeight = FontWeight.Bold) },
            text = { Text("The item has been added to your cart") },
            shape = RoundedCornerShape(16.dp),
            containerColor = Color.White
        )
    }
}