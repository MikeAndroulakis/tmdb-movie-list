package com.example.movielist.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Displays a centered loading spinner indicator.
 *
 * This composable is typically used during data fetching or screen transitions
 * to inform the user that content is currently being loaded.
 */
@Composable
fun LoadingIndicator() {
	Box(
		Modifier
			.fillMaxWidth()
			.padding(16.dp)
			.fillMaxSize(),
		contentAlignment = Alignment.Center
	) { CircularProgressIndicator() }
}