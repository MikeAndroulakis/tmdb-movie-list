package com.example.movielist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import com.example.movielist.presentation.MovieApp
import com.example.movielist.presentation.theme.MovieListTheme
import com.example.movielist.presentation.splash.SplashLoadingScreen

/**
 * The main entry point of the MovieList application.
 *
 * This activity hosts the entire Jetpack Compose UI tree and manages
 * the transition from the splash screen to the main app navigation.
 *
 * Key responsibilities:
 * - Initializes Hilt dependency injection.
 * - Displays a short splash animation using [AnimatedContent].
 * - Loads the main app content ([MovieApp]) after a short simulated delay.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Apply the app-wide theme with gradient background and color scheme
            MovieListTheme {
                // State controlling when to transition from Splash -> App
                var isReady by remember { mutableStateOf(false) }

                // Simulate a brief loading period (eg initialization)
                LaunchedEffect(Unit) {
                    delay(1500)
                    isReady = true
                }

                // Smoothly animate between splash and main content
                AnimatedContent(
                    targetState = isReady,
                    label       = "splashTransition"
                ) { ready ->
                    if (ready) {
                        // Main navigation host of the app
                        MovieApp()
                    } else {
                        // Splash screen shown while app boots
                        SplashLoadingScreen()
                    }
                }
            }
        }
    }
}