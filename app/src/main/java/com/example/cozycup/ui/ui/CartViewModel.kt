package com.example.cozycup.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.cozycup.data.MenuItem

// Representing an actual item in the cart with its custom options
data class CartItem(
    val menuItem: MenuItem,
    val size: String,
    val quantity: Int,
    val totalPrice: Double
)

class CartViewModel : ViewModel() {
    private val _items = mutableStateListOf<CartItem>()
    val items: List<CartItem> get() = _items

    fun addToCart(item: MenuItem, size: String, quantity: Int, price: Double) {
        _items.add(CartItem(item, size, quantity, price))
    }

    fun calculateGrandTotal(): Double = _items.sumOf { it.totalPrice }

    fun clearCart() { _items.clear() }
}