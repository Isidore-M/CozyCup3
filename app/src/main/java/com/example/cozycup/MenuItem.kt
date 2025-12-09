package com.example.cozycup
data class MenuItem(
    val title: String,
    val description: String,
    val price: String,
    // val imageRes: Int // We'll skip the actual image resource for now
)

// Sample Data
val sampleMenuItems = listOf(
    MenuItem("Coffee Mocha", "coffee, cream, crumbles", "10 CAD"),
    MenuItem("Coffee Matcha", "cream, sugar, crumbles", "15 CAD"),
    MenuItem("Coffee Cappucino", "coffee, cream", "10 CAD"),
    MenuItem("Coffee Black", "coffee, cream", "10 CAD"),
)
