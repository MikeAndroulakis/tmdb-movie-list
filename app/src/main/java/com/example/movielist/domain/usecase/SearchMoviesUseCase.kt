package com.example.movielist.domain.usecase

import androidx.paging.PagingData
import com.example.movielist.domain.model.Movie
import com.example.movielist.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case responsible for searching movies by a given keyword.
 *
 * This class acts as an intermediary between the ViewModel and the [MovieRepository],
 * encapsulating the business logic for performing a movie search.
 *
 * @property repository The [MovieRepository] used to retrieve movie data.
 */

class SearchMoviesUseCase @Inject constructor(
	private val repository: MovieRepository
) {
	operator fun invoke(query: String): Flow<PagingData<Movie>> =
		repository.searchMoviesPager(query)
}