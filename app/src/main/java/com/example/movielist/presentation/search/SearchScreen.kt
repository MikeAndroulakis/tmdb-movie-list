package com.example.movielist.presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.movielist.R
import com.example.movielist.domain.model.Movie
import com.example.movielist.presentation.ui.MovieImage
import com.example.movielist.presentation.ui.MovieOverview
import com.example.movielist.presentation.ui.MovieTitle
import com.example.movielist.presentation.ui.RatingValue
import com.example.movielist.presentation.ui.StarIcon
import com.example.movielist.presentation.ui.components.ErrorState
import com.example.movielist.presentation.ui.components.InlineAppendError
import com.example.movielist.presentation.ui.components.movieTextFieldColors
import com.example.movielist.presentation.ui.components.LoadingIndicator

/**
 * Main screen that allows users to search movies by keyword.
 *
 * It integrates:
 * - Search input field
 * - Lazy-loaded movie list using Paging 3
 * - Pull-to-refresh functionality
 * - Loading and error states
 * - Empty and “no results” states
 *
 * @param viewModel The [SearchViewModel] that provides search data and manages UI state.
 * @param onMovieClick Callback triggered when the user taps on a movie item.
 */

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SearchScreen(
	viewModel: SearchViewModel = hiltViewModel(),
	onMovieClick: (Movie) -> Unit
) {
	val query by viewModel.currentQuery.collectAsState()
	val movies = viewModel.movies.collectAsLazyPagingItems()

	val loadState = movies.loadState
	var isUserRefreshing by remember { mutableStateOf(false) }

	// Pull-to-refresh setup
	val pullRefreshState = rememberPullRefreshState(
		refreshing = isUserRefreshing,
		onRefresh  = {
			isUserRefreshing = true
			movies.refresh()
		}
	)

	// Stop refresh animation when loading completes
	LaunchedEffect(loadState.refresh) {
		if (loadState.refresh !is LoadState.Loading) {
			isUserRefreshing = false
		}
	}

	Column(
		modifier = Modifier
			.fillMaxSize()
			.padding(8.dp)
	) {
		// Search TextField (header)
		OutlinedTextField(
			value           = query,
			onValueChange   = { viewModel.searchMovies(it) },
			label           = { Text(stringResource(id = R.string.search_movies_label)) },
			colors          = movieTextFieldColors(),
			modifier        = Modifier.fillMaxWidth(),
			trailingIcon    = {
				AnimatedVisibility(visible = query.isNotEmpty()) {
					IconButton(onClick = { viewModel.searchMovies("") }) {
						Icon(
							imageVector         = Icons.Default.Close,
							tint                = Color.White.copy(alpha = 0.7f),
							contentDescription  = null,
						)
					}
				}
			}
		)

		Spacer(Modifier.height(8.dp))

		// Display different states depending on user input and load status
		if (query.isBlank()) {
			EmptySearchState()
		} else {
			val loadState = movies.loadState

			Box(
				modifier = Modifier
					.fillMaxSize()
					.pullRefresh(pullRefreshState)
			) {
				when {
					// --- 1. Loading state ---
					loadState.refresh is LoadState.Loading -> {
						LoadingIndicator()
					}

					// --- 2. Error state ---
					loadState.refresh is LoadState.Error -> {
						val e = (loadState.refresh as LoadState.Error).error
						ErrorState(stringResource(R.string.connection_error)) {
							movies.retry()
						}
					}

					// --- 3. No results found ---
					loadState.refresh is LoadState.NotLoading && movies.itemCount == 0 -> {
						NoMoviesWithThisNameState()
					}

					// --- 4. Success: show movie list ---
					else -> {
						LazyColumn(
							verticalArrangement = Arrangement.spacedBy(8.dp),
							modifier = Modifier.fillMaxSize()
						) {
							items(movies.itemCount) { index ->
								movies[index]?.let { movie ->
									MovieItem(movie = movie, onClick = { onMovieClick(movie) })
								}
							}

							// Handle paging append states
							when (loadState.append) {
								is LoadState.Loading -> item { LoadingIndicator() }
								is LoadState.Error -> item {
									InlineAppendError(
										message = stringResource(R.string.connection_error),
										onRetry = { movies.retry() }
									)
								}
								else -> {}
							}
						}
					}
				}

				// Pull-to-refresh indicator at top
				PullRefreshIndicator(
					refreshing      = isUserRefreshing,
					state           = pullRefreshState,
					modifier        = Modifier.align(Alignment.TopCenter),
					backgroundColor = MaterialTheme.colorScheme.surface,
					contentColor    = MaterialTheme.colorScheme.primary
				)
			}
		}
	}
}

/**
 * UI displayed when the search query is empty.
 * Shows a fun animated hint encouraging the user to start typing.
 */
@Composable
fun EmptySearchState() {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.padding(32.dp),
		contentAlignment = Alignment.Center
	) {
		// Infinite pulse animation for visual feedback
		val infiniteTransition = rememberInfiniteTransition(label = "iconPulse")

		val scale by infiniteTransition.animateFloat(
			initialValue = 0.9f,
			targetValue = 1.05f,
			animationSpec = infiniteRepeatable(
				animation = tween(durationMillis = 1200, easing = LinearEasing),
				repeatMode = RepeatMode.Reverse
			),
			label = "scaleAnim"
		)

		val alpha by infiniteTransition.animateFloat(
			initialValue = 0.8f,
			targetValue = 1f,
			animationSpec = infiniteRepeatable(
				animation = tween(durationMillis = 1200, easing = LinearEasing),
				repeatMode = RepeatMode.Reverse
			),
			label = "alphaAnim"
		)

		Column(horizontalAlignment = Alignment.CenterHorizontally) {
			Image(
				painter = painterResource(id = R.drawable.empty_movies),
				contentDescription = stringResource(R.string.no_movies_desc),
				modifier = Modifier
					.size(180.dp * scale)
					.padding(bottom = 16.dp)
			)

			Text(
				text  = stringResource(R.string.search_movies_desc),
				style = MaterialTheme.typography.titleMedium.copy(
					color       = Color.Gray.copy(alpha = 0.9f),
					fontWeight  = FontWeight.Medium,
				),
				textAlign   = TextAlign.Center,
				modifier    = Modifier
					.graphicsLayer(scaleX = scale, scaleY = scale)
					.alpha(alpha)
			)
		}
	}
}

/**
 * UI shown when a search query returns no results.
 */
@Composable
private fun NoMoviesWithThisNameState() {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.padding(32.dp),
		contentAlignment = Alignment.Center
	) {
		Column(horizontalAlignment = Alignment.CenterHorizontally) {
			Image(
				painter             = painterResource(id = R.drawable.no_movies),
				contentDescription  = "Empty movie icon",
				modifier            = Modifier
					.size(200.dp)
					.padding(bottom = 16.dp)
			)

			Text(
				text  = stringResource(R.string.no_movies_desc),
				style = MaterialTheme.typography.titleMedium.copy(
					color       = Color.Gray.copy(alpha = 0.9f),
					fontWeight  = FontWeight.Medium,
				),
				textAlign = TextAlign.Center,
			)
		}
	}
}

/**
 * Displays a single movie item within the search result list.
 *
 * @param movie The movie to display.
 * @param onClick Action triggered when the user taps the item.
 */
@Composable
private fun MovieItem(movie: Movie, onClick: () -> Unit) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.clickable { onClick() },
		colors = CardDefaults.cardColors(
			containerColor  = MaterialTheme.colorScheme.surface, // bluish transparent
			contentColor    = MaterialTheme.colorScheme.onSurface
		),
		elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
	) {
		Row(
			modifier            = Modifier.padding(8.dp).fillMaxWidth(),
			verticalAlignment   = Alignment.CenterVertically
		) {
			MovieImage(movie.posterUrl, Modifier.size(100.dp).padding(end = 8.dp))
			Column(
				verticalArrangement = Arrangement.spacedBy(4.dp),
				modifier            = Modifier.weight(1f)
			) {
				MovieTitle(movie.title, MaterialTheme.typography.titleMedium)
				MovieOverview(movie.overview, MaterialTheme.typography.bodySmall, 3)
				Row(verticalAlignment = Alignment.CenterVertically) {
					StarIcon(modifier = Modifier.size(16.dp))
					Spacer(Modifier.width(4.dp))
					RatingValue(movie.rating.toString(), MaterialTheme.typography.bodySmall)
				}
			}
		}
	}
}