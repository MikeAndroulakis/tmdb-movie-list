package com.example.movielist.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movielist.domain.model.Movie
import com.example.movielist.domain.usecase.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

/**
 * [ViewModel] responsible for managing the movie search UI state and logic.
 *
 * It communicates with the [SearchMoviesUseCase] to retrieve paginated movie data
 * and exposes it as reactive [Flow] objects to the UI.
 *
 * @property searchMoviesUseCase The use case that performs the movie search operation.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
	private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

	// Backing property for the current search query entered by the user
	private val _currentQuery = MutableStateFlow("")
	val currentQuery: StateFlow<String> = _currentQuery

	val movies: Flow<PagingData<Movie>> = _currentQuery
		.flatMapLatest { q ->
			if (q.isBlank()) flowOf(PagingData.empty()) // No query entered -> return an empty paging data
			else searchMoviesUseCase(q)                 // Fetch paginated movies for the given query
		}
		.cachedIn(viewModelScope)                               // Cache the paging stream so recompositions or configuration changes don’t restart from scratch

	fun searchMovies(newQuery: String) {
		_currentQuery.value = newQuery
	}
}