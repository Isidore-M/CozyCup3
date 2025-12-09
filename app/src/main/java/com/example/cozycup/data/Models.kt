package com.example.cozycup.data

import com.example.cozycup.R

data class MenuItem(
    val id: Int,
    val title: String,
    val description: String,     // Short sub-header (e.g., "coffee, cream")
    val longDescription: String, // NEW: Full appetizing description
    val price: String,           // String format "10 CAD"
    val category: String,
    val imageRes: Int
)

// Helper function to find a specific item by its ID
fun getMenuItemById(id: Int): MenuItem? {
    return sampleMenuItems.find { it.id == id }
}

val sampleMenuItems = listOf(
    // --- COFFEE CATEGORY ---
    MenuItem(
        id = 1,
        title = "Coffee Mocha",
        description = "coffee, cream, chocolate",
        longDescription = "A rich blend of espresso and steamed milk, infused with premium dark chocolate and topped with a light cocoa dusting for a perfect morning treat.",
        price = "10 CAD",
        category = "Coffee",
        imageRes = R.drawable.mocha
    ),
    MenuItem(
        id = 2,
        title = "Coffee Matcha",
        description = "matcha, milk, foam",
        longDescription = "Finely ground ceremonial grade green tea whisked with creamy milk for a smooth, earthy, and energizing experience that balances health and flavor.",
        price = "15 CAD",
        category = "Coffee",
        imageRes = R.drawable.matcha
    ),
    MenuItem(
        id = 3,
        title = "Cappuccino",
        description = "espresso, steamed milk",
        longDescription = "The classic balance of equal parts espresso, steamed milk, and a thick layer of airy foam, perfect for those who love a frothy finish.",
        price = "10 CAD",
        category = "Coffee",
        imageRes = R.drawable.cappuccino
    ),
    MenuItem(
        id = 4,
        title = "Black Coffee",
        description = "bold, smooth blend",
        longDescription = "Pure, bold, and smooth. Our house blend is slow-roasted to bring out deep nutty notes and a clean finish without any additives.",
        price = "10 CAD",
        category = "Coffee",
        imageRes = R.drawable.black
    ),

    // --- PASTRY CATEGORY ---
    MenuItem(
        id = 5,
        title = "Choux Pastry",
        description = "cream, sugar glaze",
        longDescription = "Light and airy French puff shells filled with a silky vanilla bean cream and topped with a delicate sugar glaze for a sophisticated sweetness.",
        price = "12 CAD",
        category = "Pastry",
        imageRes = R.drawable.choux
    ),
    MenuItem(
        id = 6,
        title = "Berry Tart",
        description = "berries, lemon curd",
        longDescription = "A buttery, crumbly shortcrust filled with zesty lemon curd and topped with a colorful medley of seasonal forest berries and fresh mint.",
        price = "15 CAD",
        category = "Pastry",
        imageRes = R.drawable.berry_tart
    ),

    // --- JUICE CATEGORY ---
    MenuItem(
        id = 7,
        title = "Kiwi Mint Juice",
        description = "kiwi, mint, lime",
        longDescription = "A refreshing tropical escape. Freshly pressed kiwi blended with cool garden mint and a splash of lime to rejuvenate your senses.",
        price = "12 CAD",
        category = "Juice",
        imageRes = R.drawable.kiwi_juice
    ),
    MenuItem(
        id = 8,
        title = "Orange Zest",
        description = "100% cold-pressed",
        longDescription = "100% cold-pressed oranges harvested at peak ripeness for a sweet, tangy, and vitamin-rich morning boost with no added sugars.",
        price = "10 CAD",
        category = "Juice",
        imageRes = R.drawable.orange_juice
    )
)