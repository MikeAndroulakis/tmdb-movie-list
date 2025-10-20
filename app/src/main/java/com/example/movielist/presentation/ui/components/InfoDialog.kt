package com.example.movielist.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

/**
 * A reusable informational dialog used to display simple alerts or messages.
 * It’s based on [AlertDialog] and is styled consistently with the MovieList app theme.
 *
 * @param title The title text displayed at the top of the dialog.
 * @param message The main message body of the dialog.
 * @param onDismiss Lambda callback triggered when the dialog is dismissed (or when “OK” is pressed).
 */
@Composable
fun InfoDialog(
	title: String,
	message: String,
	onDismiss: () -> Unit
) {
	AlertDialog(
		onDismissRequest = onDismiss,
		confirmButton = {
			Button(
				onClick     = onDismiss,
				colors      = ButtonDefaults.buttonColors(
					containerColor = Color(0xFF5E8194),
				),
				shape       = RoundedCornerShape(12.dp),
				elevation   = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
			) {
				Text(
					"OK",
					style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
				)
			}
		},
		title = {
			Text(
				text  = title,
				style = MaterialTheme.typography.titleLarge.copy(fontWeight  = FontWeight.Bold),
				textAlign = TextAlign.Center,
				modifier  = Modifier.fillMaxWidth()
			)
		},
		text = {
			Text(
				text  = message,
				textAlign = TextAlign.Center,
				modifier  = Modifier
					.fillMaxWidth()
					.padding(top = 8.dp, bottom = 16.dp)
			)
		},
		shape = RoundedCornerShape(28.dp),
		tonalElevation = 8.dp
	)
}