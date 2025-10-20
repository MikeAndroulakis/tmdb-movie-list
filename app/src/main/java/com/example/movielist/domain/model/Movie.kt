package com.example.movielist.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Domain model representing a movie entity used throughout the app.
 *
 * This model is independent of any network or database structure
 * and represents the core business data used by the UI and domain layers.
 *
 * It is marked as [Parcelable] to allow easy passing between
 * Compose navigation destinations or Android components (e.g., Intents, Bundles).
 *
 * @property id Unique identifier of the movie (from TMDB).
 * @property title The title of the movie.
 * @property overview A short plot summary or description.
 * @property posterUrl Full URL of the movie's poster image (nullable if unavailable).
 * @property rating Average user rating (0.0 – 10.0).
 */

@Parcelize
data class Movie(
	val id       : Int,
	val title    : String,
	val overview : String,
	val posterUrl: String?,
	val rating   : Double
) : Parcelable