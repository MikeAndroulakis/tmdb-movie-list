package com.example.movielist.data.remote.dto

import com.squareup.moshi.Json

/**
 * Data Transfer Object (DTO) representing the TMDB API response
 * for a movie search request.
 *
 * This is the top-level JSON structure returned by the endpoint:
 * https://api.themoviedb.org/3/search/movie
 *
 * Example JSON:
 * {
 *   "results": [...],
 *   "total_pages": 5
 * }
 *
 * @property results List of individual [MovieDto] items returned by the search.
 * @property totalPages Total number of pages available for the given query.
 */

data class MoviesDto(
	val results: List<MovieDto>,
	@Json(name = "total_pages")  val totalPages : Int
)

data class MovieDto(
	val id      : Int,
	val title   : String,
	val overview: String,
	@Json(name = "poster_path")  val posterPath : String?,
	@Json(name = "vote_average") val voteAverage: Double
)