package com.example.movielist.data.remote.api

import com.example.movielist.data.remote.dto.MoviesDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Searches TMDB for movies that match a given query keyword.
 *
 * Example request:
 * https://api.themoviedb.org/3/search/movie?api_key=XYZ&query=alien&page=1
 *
 * @param query         The keyword or phrase to search for.
 * @param page          The results page to fetch (used for pagination).
 * @param includeAdult  Whether to include adult-rated movies (default = false).
 * @param language      The language of the results (default = "en-US").
 *
 * @return [MoviesDto] object containing a list of movie results and pagination info.
 */

interface TmdbApi {
    @GET("search/movie")
    suspend fun searchMovies(
	    @Query("query")         query       : String,
	    @Query("page")          page        : Int,
	    @Query("include_adult") includeAdult: Boolean = false,
	    @Query("language")      language    : String  = "en-US"
    ): MoviesDto
}