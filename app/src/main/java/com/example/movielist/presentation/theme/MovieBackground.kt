package com.example.movielist.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/**
 * Provides a consistent linear gradient background for the MovieList app.
 *
 * This composable acts as a reusable layout wrapper that fills the entire screen
 * with a dark blue-to-teal gradient and places any passed [content] on top.
 */
@Composable
fun MovieBackground(content: @Composable () -> Unit) {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(
				brush = Brush.linearGradient(
					colors = listOf(
						Color(0xFF0F2027),
						Color(0xFF203A43),
						Color(0xFF2C5364)
					)
				)
			)
	) {
		// Render any composable passed as child content
		content()
	}
}