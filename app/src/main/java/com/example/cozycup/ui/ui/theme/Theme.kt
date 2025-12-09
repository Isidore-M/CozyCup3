package com.example.cozycup.ui.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// --- CozyCup Light Color Scheme ---
// Optimized for the bright, natural look of the design
private val LightColorScheme = lightColorScheme(
    // Primary brand color (used for buttons, icons, selected chips)
    primary = DarkGreen,
    // Accent color (optional, can be same as primary)
    secondary = MediumGreen,
    // Background for elements like the Home Screen background
    background = AppBackground,
    // Background for surfaces like Cards
    surface = Color.White,

    // Text color on primary (dark green) background
    onPrimary = Color.White,
    // Text color on the light backgrounds (surface/background)
    onBackground = Color.Black,
    onSurface = Color.Black,

    // The Welcome Screen uses LightBrown for its background, which we apply to surfaceVariant
    surfaceVariant = LightBrown
)

// --- CozyCup Dark Color Scheme (Customized for brand, even if not used immediately) ---
private val DarkColorScheme = darkColorScheme(
    primary = LightBrown,
    secondary = MediumGreen,
    background = Color(0xFF121212), // Dark app background
    surface = Color(0xFF1E1E1E),     // Dark card/surface background

    onPrimary = DarkGreen,
    onSecondary = LightBrown,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun CozyCupTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is useful, but often disabled for strong branding apps
    dynamicColor: Boolean = false, // Set to false to enforce brand colors on all devices
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Option to use dynamic colors on Android 12+ if dynamicColor is true
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        // Make sure you import or define your Typography object
        typography = Typography,
        content = content
    )
}