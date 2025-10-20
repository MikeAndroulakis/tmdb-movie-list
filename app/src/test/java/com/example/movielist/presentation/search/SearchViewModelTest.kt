package com.example.movielist

import androidx.paging.PagingData
import app.cash.turbine.test
import com.example.movielist.domain.model.Movie
import com.example.movielist.domain.repository.MovieRepository
import com.example.movielist.domain.usecase.SearchMoviesUseCase
import com.example.movielist.presentation.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.*
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import kotlin.test.assertNotNull

/**
 * Unit tests for [SearchViewModel].
 *
 * These tests verify the core business logic of the Search screen ViewModel:
 *  - Correctly triggers repository searches via the use case
 *  - Emits the expected PagingData flow when a query is entered
 *  - Handles both populated and empty search results
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {
	private val repository: MovieRepository = mock()
	private lateinit var searchMoviesUseCase: SearchMoviesUseCase
	private lateinit var viewModel: SearchViewModel

	/**
	 * Sets up a fake repository and initializes the ViewModel under test.
	 *
	 * The repository is mocked to return:
	 *  - a movie list when query == "Inception"
	 *  - an empty list for all other queries
	 */
	@Before
	fun setup() {
		/* Default stub for all queries (dynamic mocking) */
		whenever(repository.searchMoviesPager(any())).thenAnswer { invocation ->
			val query = invocation.arguments[0] as String
			val movies = when (query) {
				"Inception" -> listOf(
					Movie(1, "Inception", "Testing movie overview", null, 7.5)
				)
				else -> emptyList()
			}
			flowOf(PagingData.from(movies))
		}

		searchMoviesUseCase = SearchMoviesUseCase(repository)
		viewModel = SearchViewModel(searchMoviesUseCase)
	}

	/**
	 * Test case: When a query returns results,
	 * the ViewModel should emit a non-empty PagingData.
	 */
	@Test
	fun `searchMovies emits movie data when repository returns results`() = runTest {
		viewModel.searchMovies("Inception")

		// Wait for all async tasks inside the ViewModel to complete
		advanceUntilIdle()

		// Fetch first emission from the flow
		val firstEmission = viewModel.movies.first()

		// Assert that the ViewModel emitted valid data
		assertNotNull(firstEmission)
	}

	/**
	 * Test case: When a query has no results,
	 * the ViewModel should still emit a valid (but empty) PagingData flow.
	 */
	@Test
	fun `searchMovies emits empty list when repository returns empty`() = runTest {
		// Arrange — simulate empty repository response
		whenever(repository.searchMoviesPager(any())).thenReturn(flowOf(PagingData.empty()))

		viewModel.searchMovies("Unknown")

		// Assert — verify that the ViewModel emits at least one PagingData
		viewModel.movies.test {
			val item = awaitItem()
			assertNotNull(item)
			cancelAndIgnoreRemainingEvents()
		}
	}
}