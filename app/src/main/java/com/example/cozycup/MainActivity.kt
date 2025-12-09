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
    val cartViewModel: CartViewModel = viewModel()

    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(onGetStartedClick = { navController.navigate("home") })
        }
        composable("home") {
            HomeScreen(
                onItemClick = { id -> navController.navigate("detail/$id") },
                onCartClick = { navController.navigate("cart") }
            )
        }
        composable(
            "detail/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId")
            if (itemId != null) {
                ProductDetailScreen(
                    itemId = itemId,
                    viewModel = cartViewModel, // Passing to detail
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
        composable("cart") {
            CartScreen(
                viewModel = cartViewModel, // Passing to cart
                onBackClick = { navController.popBackStack() },
                onPlaceOrderClick = {
                    cartViewModel.clearCart()
                    navController.navigate("welcome") { popUpTo(0) }
                }
            )
        }
    }
}