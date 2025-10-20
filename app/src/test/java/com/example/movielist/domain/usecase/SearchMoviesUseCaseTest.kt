package com.example.movielist.domain.usecase

import androidx.paging.PagingData
import com.example.movielist.domain.model.Movie
import com.example.movielist.domain.repository.MovieRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.*

/**
 * Unit test for [SearchMoviesUseCase].
 *
 * This test ensures that:
 *  - The use case correctly delegates the search operation to the repository layer.
 *  - The correct query string is passed through.
 *  - The resulting Flow<PagingData<Movie>> from the repository is returned unmodified.
 */
class SearchMoviesUseCaseTest {

	// Mock repository dependency (no real data source needed)
	private val repository: MovieRepository = mock()

	/**
	 * Test case: Verify correct query delegation
	 *
	 * Ensures that when the use case is invoked with a query,
	 * it calls the corresponding repository function with that query
	 * and returns the same Flow emitted by the repository.
	 */
	@Test
	fun `invoke calls repository with correct query`() = runTest {
		val expectedFlow = flowOf(PagingData.from(listOf(Movie(1,"A","",null,8.0))))
		whenever(repository.searchMoviesPager("alien")).thenReturn(expectedFlow)

		val useCase = SearchMoviesUseCase(repository)
		val result = useCase("alien")

		// Assert — confirm that:
		// (1) The same flow object was returned,
		// (2) The repository was called exactly once with the same query.
		assertEquals(expectedFlow, result)
		verify(repository).searchMoviesPager("alien")
	}
}