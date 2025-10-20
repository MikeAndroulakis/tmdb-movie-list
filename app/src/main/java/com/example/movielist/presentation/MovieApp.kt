package com.example.movielist.presentation

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.res.stringResource
import com.example.movielist.R
import com.example.movielist.domain.model.Movie
import com.example.movielist.presentation.detail.DetailScreen
import com.example.movielist.presentation.search.SearchScreen
import com.example.movielist.presentation.ui.components.InfoDialog
import java.util.Locale

/**
 * The main composable that defines the navigation flow for the MovieList app.
 *
 * It uses a [NavHost] to handle transitions between:
 * - **SearchScreen**: The main screen where users search for movies.
 * - **DetailScreen**: The detailed view of a selected movie.
 *
 * It also manages transient UI states such as showing an info dialog
 * when the selected movie has incomplete data.
 */
@Composable
fun MovieApp() {
    // Remember and manage navigation controller for in-app routes
    val navController = rememberNavController()

    // Define navigation graph with start destination
    NavHost(
        navController = navController,
        startDestination = "search"
    ) {
        /** -------------------- SEARCH SCREEN -------------------- **/
        composable("search") {
            // UI state for controlling the InfoDialog visibility
            var showDialog by remember { mutableStateOf(false) }

            SearchScreen(
                onMovieClick = { movie ->
                    if (movie.title.isBlank() || movie.overview.isBlank()) {
                        showDialog = true // Show dialog if movie data is incomplete
                    } else {
                        // Store Parcelable Movie object into back stack state
                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("movie", movie)

                        // Navigate to the detail screen
                        navController.navigate("detail")
                    }
                }
            )

            // Display dialog when invalid movie data is detected
            if (showDialog) {
                InfoDialog(
                    title       = stringResource(R.string.missing_data),
                    message     = stringResource(R.string.missing_data_desc),
                    onDismiss   = { showDialog = false }
                )
            }
        }

        /** -------------------- DETAIL SCREEN -------------------- **/
        composable("detail") { backStackEntry ->
            // Retrieve the saved Movie from the previous back stack entry
            val movie = remember {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<Movie>("movie")
            }

            // Only show the detail screen if movie data is available
            movie?.let {
                DetailScreen(
                    title       = it.title,
                    overview    = it.overview,
                    posterUrl   = it.posterUrl,
                    rating      = String.format(Locale.US, "%.1f", it.rating),
                    onBack      = { navController.popBackStack() }
                )
            }
        }
    }
}