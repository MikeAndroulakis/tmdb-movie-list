package com.example.movielist.domain.repository

import androidx.paging.PagingData
import com.example.movielist.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
	/**
	 * Returns a [Flow] of paginated [Movie] data matching the given search query.
	 *
	 * The results are wrapped in [PagingData], allowing efficient on-demand loading
	 * as the user scrolls through the movie list.
	 *
	 * @param query The keyword or title to search for.
	 * @return A cold [Flow] that emits [PagingData] objects containing [Movie] items.
	 */

	fun searchMoviesPager(query: String): Flow<PagingData<Movie>>
}