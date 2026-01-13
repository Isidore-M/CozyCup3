package com.example.cozycup.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Represents the different UI states of the Checkout screen.
 */
sealed class CheckoutUiState {
    object Idle : CheckoutUiState()
    object Loading : CheckoutUiState()
    data class Success(val params: PaymentParams) : CheckoutUiState()
    data class Error(val message: String) : CheckoutUiState()
}

/**
 * Data needed by Stripe's PaymentSheet to process a transaction.
 */
data class PaymentParams(
    val clientSecret: String,
    val customerId: String,
    val ephemeralKey: String
)

class CheckoutViewModel : ViewModel() {

    // 1. Private internal state
    private val _uiState = MutableStateFlow<CheckoutUiState>(CheckoutUiState.Idle)

    // 2. Public read-only state for the Compose UI to observe
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    /**
     * Call this when the user clicks "Pay Now".
     * In a real app, this connects to your server to get Stripe keys.
     */
    fun preparePayment(amount: Double) {
        viewModelScope.launch {
            // Start loading state
            _uiState.value = CheckoutUiState.Loading

            try {
                // SIMULATED API CALL:
                // This is where you'd use Retrofit or Ktor to call your backend.
                delay(2000)

                // MOCK SUCCESS DATA:
                // Note: In production, these 3 values MUST come from your server.
                val mockResponse = PaymentParams(
                    clientSecret = "pi_mock_secret_123",
                    customerId = "cus_mock_user_456",
                    ephemeralKey = "ek_test_mock_789"
                )

                _uiState.value = CheckoutUiState.Success(mockResponse)

            } catch (e: Exception) {
                // If the internet fails or server is down
                _uiState.value = CheckoutUiState.Error("Connection failed. Check your cozy internet!")
            }
        }
    }

    /**
     * Resets the state so the user can try again if there was an error.
     */
    fun resetState() {
        _uiState.value = CheckoutUiState.Idle
    }
}