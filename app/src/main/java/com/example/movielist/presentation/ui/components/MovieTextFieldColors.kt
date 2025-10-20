package com.example.movielist.presentation.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Provides consistent color styling for text fields across the MovieList app.
 *
 * This function centralizes all color definitions used by [TextField] and [OutlinedTextField],
 * ensuring a cohesive look aligned with the app's dark, cinematic theme.
 *
 * It customizes:
 * - Text color and alpha transparency for focused/unfocused states
 * - Cursor and indicator colors
 * - Label, placeholder, and supporting text styling
 * - Prefix/Suffix tints
 *
 * @return A [TextFieldColors] object that defines all color properties for consistent UI styling.
 */
@Composable
fun movieTextFieldColors(): TextFieldColors {
	val colorScheme = MaterialTheme.colorScheme

	return TextFieldDefaults.colors(
		// Text
		focusedTextColor    = Color.White.copy(alpha = 0.85f),
		unfocusedTextColor  = Color.White.copy(alpha = 0.7f),

		// Container (background)
		focusedContainerColor   = Color.Transparent,
		unfocusedContainerColor = Color.Transparent,

		// Cursor
		cursorColor         = Color.White.copy(alpha = 0.85f),
		errorCursorColor    = colorScheme.error,

		// Indicators (the outline for OutlinedTextField)
		focusedIndicatorColor   = Color.White.copy(alpha = 0.85f),
		unfocusedIndicatorColor = colorScheme.outline.copy(alpha = 0.7f),
		disabledIndicatorColor  = colorScheme.outline.copy(alpha = 0.3f),
		errorIndicatorColor     = colorScheme.error,

		// Labels
		focusedLabelColor   = Color.White,
		unfocusedLabelColor = Color.White.copy(alpha = 0.7f),

		// Placeholder
		focusedPlaceholderColor     = Color.White.copy(alpha = 0.6f),
		unfocusedPlaceholderColor   = Color.White.copy(alpha = 0.5f),

		// Supporting text
		focusedSupportingTextColor  = Color.White.copy(alpha = 0.7f),
		unfocusedSupportingTextColor= Color.White.copy(alpha = 0.7f),

		// Prefix / Suffix
		focusedPrefixColor      = Color.White,
		unfocusedPrefixColor    = Color.White.copy(alpha = 0.8f),
		focusedSuffixColor      = Color.White,
		unfocusedSuffixColor    = Color.White.copy(alpha = 0.8f)
	)
}