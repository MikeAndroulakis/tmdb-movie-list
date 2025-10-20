package com.example.movielist.data.remote

import androidx.paging.PagingSource
import com.example.movielist.data.remote.api.TmdbApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


/**
 * Unit tests for [MoviesPagingSource].
 *
 * These tests verify that the PagingSource correctly handles:
 *  - Successful API responses (returns a Page)
 *  - Server errors (returns an Error)
 *
 * It uses [MockWebServer] to simulate TMDB API responses locally
 * without making real network calls.
 */
class MoviesPagingSourceTest {
	private lateinit var server: MockWebServer
	private lateinit var api: TmdbApi

	/**
	 * Sets up a local mock HTTP server and a Retrofit instance
	 * using a Moshi converter before each test.
	 */
	@Before
	fun setup() {
		server = MockWebServer().apply { start() }

		val moshi = Moshi.Builder()
			.add(KotlinJsonAdapterFactory())
			.build()

		// Build Retrofit to point to the mock server
		val retrofit = Retrofit.Builder()
			.baseUrl(server.url("/"))
			.addConverterFactory(MoshiConverterFactory.create(moshi))
			.build()


		api = retrofit.create(TmdbApi::class.java)
	}

	/**
	 * Shuts down the mock server after each test to release resources.
	 */
	@After
	fun teardown() = server.shutdown()

	/**
	 * Test case: Successful response (HTTP 200)
	 *
	 * Ensures that when the API returns a valid JSON payload,
	 * the PagingSource emits a [PagingSource.LoadResult.Page] with
	 * correctly mapped movie data.
	 */
	@Test
	fun `returns page on success`() = runTest {
		val body = """
            {"results":[{"id":1,"title":"Alien","overview":"Classic","poster_path":"/a.jpg","vote_average":7.5}],
             "total_pages":1}
        """.trimIndent()

		server.enqueue(MockResponse().setResponseCode(200).setBody(body))

		val pagingSource = MoviesPagingSource(api, "alien")
		val result = pagingSource.load(
			PagingSource.LoadParams.Refresh(null, 20, false)
		)

		assertTrue(result is PagingSource.LoadResult.Page)
		val page = result as PagingSource.LoadResult.Page
		assertEquals(1, page.data.size)
		assertEquals("Alien", page.data.first().title)
	}

	/**
	 * Test case: Server error (HTTP 500)
	 *
	 * Ensures that when the API responds with a 500 error,
	 * the PagingSource emits a [PagingSource.LoadResult.Error].
	 */
	@Test
	fun `returns error on 500 response`() = runTest {
		server.enqueue(MockResponse().setResponseCode(500))
		val pagingSource = MoviesPagingSource(api, "alien")

		val result = pagingSource.load(
			PagingSource.LoadParams.Refresh(null, 20, false)
		)

		assertTrue(result is PagingSource.LoadResult.Error)
	}
}