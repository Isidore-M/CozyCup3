package com.example.cozycup.ui.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cozycup.R // Assume you have R.drawable.coffee_hero
import com.example.cozycup.ui.ui.theme.DarkGreen
import com.example.cozycup.ui.ui.theme.LightBrown
import com.example.cozycup.ui.ui.theme.MediumGreen

@Composable
fun WelcomeScreen(onGetStartedClick: () -> Unit) {
    // We use a Column and fill the max size, applying the background here.
    Column(
        modifier = Modifier
            .fillMaxSize() // <-- Now takes up the whole screen width
            .background(LightBrown)
            .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally

    ) {
        // 1. Logo/Title Area
        Spacer(modifier = Modifier.height(118.dp))
        Text(
            text = "CozyCup",
            color = MediumGreen,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = "Fresh brews. Fresh bakes.Fresh vibes.",
            color = DarkGreen.copy(alpha = 0.8f),
            fontSize = 9.sp,
            modifier = Modifier.padding(top = 4.dp),

        )

        // 2. Hero Image Placeholder
        // We use weights to push the image and text blocks apart vertically
        Spacer(modifier = Modifier.weight(0.3f))
        Image(
            painter = painterResource(id = R.drawable.homecoffee),
            contentDescription = "Coffee and Croissant",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
        Spacer(modifier = Modifier.weight(1f))

        // 3. Text Block
        Text(
            text = "CozyCup brings you\ncomfort in every sip and\nsweetness in every bite.",
            color = DarkGreen,
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 36.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Welcome to CozyCup! Sit back, relax, and enjoy our handcrafted coffees and freshly baked pastries made with love.",
            color = DarkGreen.copy(alpha = 0.7f),
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 16.dp),
            textAlign = TextAlign.Center
        )

        // 4. Action Button
        Button(
            onClick = onGetStartedClick,
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp)
                .height(56.dp)
        ) {
            Text(text = "Get started", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        }
        Spacer(modifier = Modifier.weight(0.8f))
    }
}