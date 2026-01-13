package com.example.cozycup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cozycup.ui.CartViewModel
import com.example.cozycup.ui.screens.*
import com.example.cozycup.ui.ui.screens.WelcomeScreen
import com.example.cozycup.ui.ui.theme.CozyCupTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CozyCupTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CoffeeAppNavigation()
                }
            }
        }
    }
}

@Composable
fun CoffeeAppNavigation() {
    val navController = rememberNavController()

    // Single source of truth for the entire app session
    val cartViewModel: CartViewModel = viewModel()

    NavHost(navController = navController, startDestination = "welcome") {

        // --- 1. Welcome Screen ---
        composable("welcome") {
            WelcomeScreen(onGetStartedClick = { navController.navigate("home") })
        }

        // --- 2. Home Screen ---
        composable("home") {
            HomeScreen(
                viewModel = cartViewModel,
                onItemClick = { id -> navController.navigate("detail/$id") },
                onCartClick = { navController.navigate("cart") }
            )
        }

        // --- 3. Product Detail Screen ---
        composable(
            "detail/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId")
            if (itemId != null) {
                ProductDetailScreen(
                    itemId = itemId,
                    viewModel = cartViewModel,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }

        // --- 4. Cart Screen ---
        composable("cart") {
            CartScreen(
                viewModel = cartViewModel,
                onBackClick = { navController.popBackStack() },
                onPlaceOrderClick = {
                    // NEW: Navigate to checkout instead of resetting
                    navController.navigate("checkout")
                }
            )
        }

        // --- 5. Checkout Screen (NEW) ---
        composable("checkout") {
            CheckoutScreen(
                viewModel = cartViewModel,
                onBackClick = { navController.popBackStack() },
                onConfirmOrder = {
                    // Logic: Clear the cart and go back to welcome after payment
                    cartViewModel.clearCart()
                    navController.navigate("welcome") {
                        popUpTo(0) // Clears the entire backstack
                    }
                }
            )
        }
    }
}