package com.example.movielist.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.movielist.R
import com.example.movielist.presentation.ui.MovieImage
import com.example.movielist.presentation.ui.MovieOverview
import com.example.movielist.presentation.ui.RatingSection

/**
 * UI screen that displays detailed information about a selected movie.
 *
 * This composable shows the movie's:
 * - Poster image
 * - Title in the top app bar
 * - Overview (description)
 * - Rating value
 *
 * @param title The title of the selected movie.
 * @param overview The movie's description text.
 * @param posterUrl The full URL of the movie poster (nullable if unavailable).
 * @param rating The movie’s average rating as a formatted string.
 * @param onBack Callback triggered when the user presses the back button.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
	title       : String,
	overview    : String,
	posterUrl   : String?,
	rating      : String,
	onBack      : () -> Unit
) {
	Scaffold( // Scaffold provides a consistent page layout structure with a top bar and content area
		topBar = {
			CenterAlignedTopAppBar(
				title = { // Display the movie title in the toolbar (ellipsized if too long)
					Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis)
				},
				navigationIcon = { // Back button to navigate to previous screen
					TextButton(onClick = onBack) {
						Text(stringResource(id = R.string.back))
					}
				}
			)
		}
	) { padding ->
		Column( // Main scrollable content area
			verticalArrangement = Arrangement.spacedBy(12.dp),
			modifier = Modifier
				.padding(padding)
				.padding(16.dp)
				.verticalScroll(rememberScrollState())
		) {
			MovieImage(posterUrl, Modifier.fillMaxWidth().height(320.dp))
			RatingSection(rating = rating, style = MaterialTheme.typography.titleMedium)
			MovieOverview(overview, MaterialTheme.typography.bodyMedium)
		}
	}
}