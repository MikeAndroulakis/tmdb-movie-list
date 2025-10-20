package com.example.movielist.presentation.ui

import com.example.movielist.R
import androidx.compose.foundation.layout.*
import androidx.compose.material.DrawerDefaults.shape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movielist.presentation.theme.Amber500

/**
 * Displays the movie title text with the provided [TextStyle].
 */
@Composable
fun MovieTitle(title: String, style: TextStyle) {
	Text(text = title, style = style)
}

/**
 * Displays the movie overview/description text.
 *
 * @param overview The movie synopsis or short description.
 * @param style The typography style used for the text.
 * @param maxLines Optional limit of visible lines (default is unlimited).
 */
@Composable
fun MovieOverview(overview: String, style: TextStyle, maxLines : Int? = null) {
	Text(text = overview, style = style, maxLines = maxLines ?: Int.MAX_VALUE)
}

/**
 * Loads and displays the movie poster image using.
 *
 * Automatically handles:
 * - Crossfade animation when loading
 * - Placeholder image while loading
 * - Error fallback image if loading fails
 *
 * @param posterUrl The URL of the movie poster.
 * @param modifier The layout modifier applied to the image.
 */
@Composable
fun MovieImage(posterUrl: String?, modifier: Modifier) {
	AsyncImage(
		model               = ImageRequest.Builder(LocalContext.current)
			.data(posterUrl)
			.crossfade(true)
			.build(),
		contentDescription  = null,
		contentScale        = ContentScale.Crop,
		modifier            = modifier.clip(shape),
		placeholder         = painterResource(R.drawable.loading_image),
		error               = painterResource(R.drawable.no_image)
	)
}

/**
 * Displays a horizontal row showing the rating label, star icon, and rating value.
 *
 * @param rating The numeric rating (e.g., "8.4").
 * @param modifier Optional layout modifier for positioning.
 * @param style The text style for labels and values.
 */
@Composable
fun RatingSection(rating: String, modifier: Modifier = Modifier, style: TextStyle) {
	Row(
		verticalAlignment   = Alignment.CenterVertically,
		modifier            = modifier
	) {
		RatingLabel(rating, style)
		Spacer(Modifier.width(6.dp))
		StarIcon()
		Spacer(Modifier.width(6.dp))
		RatingValue(rating, style)
	}
}

/**
 * Displays the “Rating” label text.
 */
@Composable
fun RatingLabel(rating: String, style: TextStyle) {
	Text(
		text  = stringResource(R.string.rating, rating),
		style = style
	)
}

/**
 * Displays the numeric rating value (e.g. "8.5").
 */
@Composable
fun RatingValue(rating: String, style: TextStyle) {
	Text(
		text  = rating,
		style = style
	)
}

/**
 * Displays a gold star icon used next to movie ratings.
 */
@Composable
fun StarIcon(modifier: Modifier = Modifier) {
	Icon(
		imageVector         = Icons.Filled.Star,
		contentDescription  = stringResource(id = R.string.rating_icon_desc),
		tint                = Amber500,
		modifier            = modifier.size(20.dp)
	)
}