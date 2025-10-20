package com.example.movielist.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.movielist.R

/**
 * Displays a full-screen error message with a retry button.
 *
 * Typically used when the app fails to load a critical screen (e.g. network failure).
 *
 * @param message The error message to display.
 * @param onRetry Callback invoked when the user taps the “Retry” button.
 */
@Composable
fun ErrorState(
	message: String,
	onRetry: () -> Unit
) {
	Column(
		Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		Text(
			message,
			style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
			textAlign = TextAlign.Center
		)
		Spacer(Modifier.height(12.dp))
		Button(
			onClick = onRetry,
			colors = ButtonDefaults.buttonColors(
				containerColor = Color(0xFFE53935),
				contentColor = Color.White
			)
		) {
			Text(stringResource(R.string.retry))
		}
	}
}

/**
 * Displays an inline error message with a retry action,
 * usually placed at the bottom of a paginated list.
 *
 * Used when a network error occurs while loading *additional* content,
 * but not the initial screen (e.g. failed paging append).
 *
 * @param message The inline error message text.
 * @param onRetry Callback executed when the user taps the “Retry” button.
 */
@Composable
fun InlineAppendError(
	message: String,
	onRetry: () -> Unit
) {
	Column(
		Modifier
			.fillMaxWidth()
			.padding(16.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Text(
			message,
			style = MaterialTheme.typography.bodyMedium,
			textAlign = TextAlign.Center
		)
		Spacer(Modifier.height(8.dp))
		OutlinedButton(
			onClick = onRetry,
			colors = ButtonDefaults.buttonColors(
				containerColor = Color(0xFFE53935),
				contentColor = Color.White
			)
		) {
			Text(stringResource(R.string.retry))
		}
	}
}