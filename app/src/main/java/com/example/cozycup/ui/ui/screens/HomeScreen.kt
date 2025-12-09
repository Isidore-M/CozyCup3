package com.example.cozycup.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ShoppingCart
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
import com.example.cozycup.data.MenuItem
import com.example.cozycup.data.sampleMenuItems
import com.example.cozycup.ui.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onItemClick: (Int) -> Unit, onCartClick: () -> Unit) {
    // State to track which category is selected
    var selectedCategory by remember { mutableStateOf("Coffee") }
    val categories = listOf("Coffee", "Pastries", "Juices")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // Brand title from design [cite: 7]
                    Text(
                        text = "CozyCup",
                        color = DarkGreen,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    // Cart Icon (Top Right)
                    IconButton(onClick = onCartClick) {
                        BadgedBox(
                            badge = {
                                // Badge indicating items in cart (Static for now)
                                Badge(containerColor = red) { Text("2", color = Color.White) }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "View Cart",
                                tint = DarkGreen
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AppBackground)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppBackground)
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            // Greeting text from the design [cite: 8]
            Text(
                text = "Good morning, place your order now",
                color = TextGray,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Category section header [cite: 6, 21, 37]
            Text(
                text = "Categories",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black
            )

            // Horizontal list for category selection
            LazyRow(
                modifier = Modifier.padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    CategoryChip(
                        name = category,
                        isSelected = selectedCategory == category,
                        onClick = { selectedCategory = category }
                    )
                }
            }

            // Filtering the menu based on the selected category [cite: 24, 25, 26, 40, 41, 42]
            val filteredItems = sampleMenuItems.filter { it.category == selectedCategory }

            // Grid displaying product cards
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredItems) { item ->
                    ProductCard(
                        item = item,
                        onClick = { onItemClick(item.id) }
                    )
                }
            }
        }
    }
}

// --- Helper Composables ---

@Composable
fun CategoryChip(name: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.clickable { onClick() },
        // Selected chip uses DarkGreen; unselected uses White [cite: 86]
        color = if (isSelected) DarkGreen else Color.White,
        shape = RoundedCornerShape(20.dp),
        shadowElevation = 2.dp
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
            color = if (isSelected) Color.White else Color.Black,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ProductCard(item: MenuItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        // Card background matches the light-cream theme
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            // Product Image
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Product Information [cite: 12, 13, 16, 18, 27, 43]
            Text(
                text = item.title,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black
            )
            Text(
                text = item.description,
                color = TextGray,
                fontSize = 11.sp,
                maxLines = 1
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Price label [cite: 12, 17, 27, 44]
                Text(
                    text = item.price,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen
                )

                // Add Button (+) [cite: 14, 15, 19, 20, 30, 31, 35, 36, 47, 48, 54, 55]
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(DarkGreen, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add to cart",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}