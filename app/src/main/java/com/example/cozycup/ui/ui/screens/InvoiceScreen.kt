package com.example.cozycup.ui.ui.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cozycup.ui.CartViewModel
import com.example.cozycup.ui.ui.theme.*
import java.util.UUID

@Composable
fun InvoiceScreen(
    viewModel: CartViewModel,
    paymentMethod: String,
    onFinish: () -> Unit
) {
    val orderId = "CZC-${UUID.randomUUID().toString().take(8).uppercase()}"
    val subtotal = viewModel.calculateGrandTotal()
    val tax = subtotal * 0.13 // 13% HST for Ontario
    val total = subtotal + tax

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(65.dp))
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = DarkGreen,
            modifier = Modifier.size(80.dp)
        )
        Text("Payment Received!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Order $orderId", color = TextGray, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(32.dp))

        // Receipt Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text("Invoice Details", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Divider(modifier = Modifier.padding(vertical = 12.dp))

                // List of items
                viewModel.items.forEach { item ->
                    Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                        Text("${item.quantity}x ${item.menuItem.title} (${item.size})")
                        Text("${item.totalPrice.toInt()} CAD")
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 12.dp))

                // Financial Breakdown
                PriceRow("Subtotal", subtotal)
                PriceRow("Tax (HST 13%)", tax)
                PriceRow("Total", total, isBold = true)

                Spacer(modifier = Modifier.height(16.dp))
                Text("Paid via $paymentMethod", color = DarkGreen, fontWeight = FontWeight.Medium)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onFinish,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Done", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun PriceRow(label: String, amount: Double, isBold: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal)
        Text(
            text = "${String.format("%.2f", amount)} CAD",
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
    }
}