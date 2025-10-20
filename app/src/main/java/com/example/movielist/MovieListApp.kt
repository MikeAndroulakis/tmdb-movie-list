package com.example.movielist

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Base Application class for the MovieList app.
 *
 * This class serves as the root entry point for **Hilt Dependency Injection**.
 *
 * It ensures that:
 * - All `@AndroidEntryPoint` annotated Activities, Fragments, and ViewModels
 *   can receive their dependencies automatically.
 * - The global application context can be safely injected when required.
 */
@HiltAndroidApp // Marks this Application as the Hilt entry point
class MovieListApp : Application()