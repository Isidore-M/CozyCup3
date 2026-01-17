package com.example.cozycup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
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
import com.example.cozycup.ui.ui.screens.InvoiceScreen
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
    val cartViewModel: CartViewModel = viewModel()

    // Temporary state to hold the payment choice for the receipt
    var selectedPaymentMethod by remember { mutableStateOf("Card") }

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
                    navController.navigate("checkout")
                }
            )
        }

        // --- 5. Checkout Screen (UPDATED with dummy logic support) ---
        composable("checkout") {
            CheckoutScreen(
                viewModel = cartViewModel,
                onBackClick = { navController.popBackStack() },
                onPaymentSuccess = { method ->
                    // Capture the method (Card or PayPal) and go to Invoice
                    selectedPaymentMethod = method
                    navController.navigate("invoice")
                }
            )
        }

        // --- 6. Invoice Screen (NEW) ---
        composable("invoice") {
            InvoiceScreen(
                viewModel = cartViewModel,
                paymentMethod = selectedPaymentMethod,
                onFinish = {
                    // Final Step: Clear the cart and reset to Home
                    cartViewModel.clearCart()
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }
    }
}