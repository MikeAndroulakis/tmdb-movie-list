package com.example.movielist.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movielist.data.remote.MoviesPagingSource
import com.example.movielist.data.remote.api.TmdbApi
import com.example.movielist.domain.model.Movie
import com.example.movielist.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
	private val api: TmdbApi
) : MovieRepository {

	/**
	 * Returns a [Flow] of [PagingData] containing movies that match the search query.
	 *
	 * The Paging library automatically loads pages as needed, using
	 * [MoviesPagingSource] to retrieve data from the TMDB API.
	 *
	 * @param query The movie title or keyword to search for.
	 * @return A [Flow] that emits [PagingData] objects containing [Movie] items.
	 *
	 * PagingConfig parameters:
	 * - `pageSize`: Number of items per API page (20 items per TMDB page).
	 * - `prefetchDistance`: Number of items before the end of the list to trigger next load.
	 * - `enablePlaceholders`: Disabled to simplify UI rendering.
	 */

	override fun searchMoviesPager(query: String): Flow<PagingData<Movie>> =
		Pager(
			pagingSourceFactory = { MoviesPagingSource(api, query) },
			config              = PagingConfig(
				pageSize            = 20,
				prefetchDistance    = 2,
				enablePlaceholders  = false
			)
		).flow
}