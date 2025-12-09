package com.example.cozycup.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cozycup.ui.CartViewModel
import com.example.cozycup.ui.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(viewModel: CartViewModel, onBackClick: () -> Unit, onPlaceOrderClick: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("My Cart") }, navigationIcon = { IconButton(onClick = onBackClick) { Icon(Icons.Default.ArrowBack, null) } }) },
        bottomBar = {
            Column(Modifier.padding(24.dp)) {
                Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Text("Total")
                    Text("${viewModel.calculateGrandTotal().toInt()} CAD", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
                Button(onClick = onPlaceOrderClick, Modifier.fillMaxWidth().padding(top = 16.dp), colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)) {
                    Text("Place order")
                }
            }
        }
    ) { p ->
        LazyColumn(Modifier.padding(p).padding(24.dp)) {
            items(viewModel.items) { item ->
                Row(Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                    Image(painterResource(item.menuItem.imageRes), null, Modifier.size(80.dp).clip(RoundedCornerShape(8.dp)), contentScale = ContentScale.Crop)
                    Column(Modifier.padding(start = 16.dp)) {
                        Text(item.menuItem.title, fontWeight = FontWeight.Bold)
                        Text("${item.size} x ${item.quantity}", color = Color.Gray)
                        Text("${item.totalPrice.toInt()} CAD", color = DarkGreen)
                    }
                }
            }
        }
    }
}