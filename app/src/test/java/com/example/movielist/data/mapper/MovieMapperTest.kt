package com.example.movielist.data.mapper

import com.example.movielist.data.remote.dto.MovieDto
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit test for the MovieMapper extension function `toDomain()`.
 *
 * Ensures that MovieDto objects are correctly converted into
 * domain-level Movie models, preserving data integrity and
 * applying the correct transformations
 */
class MovieMapperTest {

	/**
	 * Verifies that `toDomain()` correctly maps all fields
	 * from the MovieDto (data layer) to the Movie (domain layer).
	 */
	@Test
	fun `toDomain maps all fields correctly`() {
		val dto = MovieDto(
			id          = 1,
			title       = "Alien",
			overview    = "Alien overview",
			posterPath  = "/alien.jpg",
			voteAverage = 7.5
		)

		val movie = dto.toDomain()

		assertEquals(1, movie.id)
		assertEquals("Alien", movie.title)
		assertEquals("Alien overview", movie.overview)
		assertEquals("https://image.tmdb.org/t/p/w500/alien.jpg", movie.posterUrl)
		assertEquals(7.5, movie.rating, 0.001)
	}
}