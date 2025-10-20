package com.example.movielist.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movielist.data.mapper.toDomain
import com.example.movielist.data.remote.api.TmdbApi
import com.example.movielist.domain.model.Movie
import retrofit2.HttpException
import java.io.IOException

/**
 * PagingSource implementation responsible for loading paginated movie data
 * from the TMDB API based on a search query.
 *
 * This class connects the [TmdbApi] with the Android Paging 3 library.
 * It defines how to load chunks ("pages") of movies from the network
 * as the user scrolls through the list.
 *
 * @property api The [TmdbApi] instance used to make network calls.
 * @property query The search keyword entered by the user.
 */

class MoviesPagingSource(
    private val api  : TmdbApi,
    private val query: String
) : PagingSource<Int, Movie>() {

    /* Loads a single page of data from the TMDB API */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        return try {
            val response = api.searchMovies(query = query, page = page)
            val movies   = response.results.map { it.toDomain() }

            LoadResult.Page(
                data    = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page < response.totalPages) page + 1 else null
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { pos ->
            val page = state.closestPageToPosition(pos)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }
}