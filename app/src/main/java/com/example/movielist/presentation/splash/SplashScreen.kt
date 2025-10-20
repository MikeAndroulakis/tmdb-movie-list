package com.example.movielist.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.movielist.R

/**
 * Displays a simple splash/loading screen while the app is initializing.
 *
 * The UI features:
 * - A **vertical gradient background** with cool-toned colors.
 * - A **CircularProgressIndicator** in the center.
 * - A **loading message** below the spinner.
 */
@Composable
fun SplashLoadingScreen() {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(
				Brush.linearGradient(
					listOf(
						Color(0xFF0F2027),
						Color(0xFF203A43),
						Color(0xFF2C5364)
					)
				)
			),
		contentAlignment = Alignment.Center
	) {
		Column(horizontalAlignment = Alignment.CenterHorizontally) {
			CircularProgressIndicator(
				color       = Color.White,
				strokeWidth = 3.dp
			)
			Spacer(Modifier.height(12.dp))
			Text(
				text  = stringResource(R.string.loading_info) + "...",
				color = Color.White.copy(alpha = 0.8f),
				style = MaterialTheme.typography.titleMedium.copy(
					fontWeight = FontWeight.Medium,
					textAlign  = TextAlign.Center
				)
			)
		}
	}
}