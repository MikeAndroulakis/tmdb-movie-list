package com.example.movielist.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

/**
 * Defines and applies the global Material 3 theme for the MovieList app.
 *
 * This includes:
 * - **Custom dark & light color palettes** tailored for a cinematic look.
 * - A **gradient background** wrapper applied via [MovieBackground].
 *
 * The theme ensures visual consistency across all screens and components
 * while allowing dynamic adaptation to system-wide dark mode settings.
 *
 * @param darkTheme Whether to use the dark color scheme. Defaults to system preference.
 * @param content The composable content wrapped by this theme.
 */
@Composable
fun MovieListTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Select appropriate color scheme based on system settings
    val colorScheme = when {
        darkTheme   -> DarkColorScheme
        else        -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = {
            MovieBackground {
                content()
            }
        }
    )
}

/* --------------------------- COLOR SCHEMES --------------------------- */

/**
 * Dark color scheme for the app.
 */
private val DarkColorScheme = darkColorScheme(
    primary             = White85,              // Primary brand color (used for main interactive elements like buttons, switches, sliders, and app bars)
    onPrimary           = White85,              // Color for content (text, icons) displayed on top of primary elements

    secondary           = CinematicBlueGray,    // Secondary accent color (used for chips, filters, badges, and secondary actions)
    onSecondary         = White85,              // Color for content displayed on top of secondary elements

    background          = Color.Transparent,    // Overall screen background (Scaffold / root level); gradient handled externally
    onBackground        = White85,              // Color for text, icons, and outlines that sit directly on the background

    surface             = DeepSlateBlue,        // Background color for surfaces such as cards, panels, and menus
    onSurface           = White85,              // Color for text and icons displayed on top of surface components
    surfaceVariant      = DeepSlateBlue,        // Alternative surface color for higher-elevated surfaces (e.g. dialogs, popups)
    onSurfaceVariant    = White85,              // Text and icon color for content placed on top of surfaceVariant components
    surfaceContainerHigh= DeepSlateBlue,        // Background color for highly elevated containers like dialogs or sheets

    error               = DeepCrimson,          // Color used to indicate errors (validation, alerts, destructive actions)
    onError             = White85,              // Color for content displayed on top of error backgrounds

)

/**
 * Light color scheme for the app
 * Offering higher brightness for better visibility.
 */
private val LightColorScheme = lightColorScheme(
    primary             = White,            // Primary brand color (used for main interactive elements like buttons, switches, sliders, and app bars)
    onPrimary           = White,            // Color for content (text, icons) displayed on top of primary elements

    secondary           = SteelTeal,        // Secondary accent color (used for chips, filters, badges, and secondary actions)
    onSecondary         = White,            // Color for content displayed on top of secondary elements

    background          = Color.Transparent,// Overall screen background (Scaffold / root level); gradient handled externally
    onBackground        = White,            // Color for text, icons, and outlines that sit directly on the background

    surface             = CinematicSteel,   // Background color for surfaces such as cards, panels, and menus
    onSurface           = White,            // Color for text and icons displayed on top of surface components
    surfaceVariant      = CinematicSteel,   // Alternative surface color for higher-elevated surfaces (e.g. dialogs, popups)
    onSurfaceVariant    = White,            // For dialog text
    surfaceContainerHigh= CinematicSteel,   // Background color for highly elevated containers like dialogs or sheets

    error               = SoftCinematicRed, // Color used to indicate errors (validation, alerts, destructive actions)
    onError             = White,            // Color for content displayed on top of error backgrounds
)