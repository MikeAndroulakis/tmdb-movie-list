package com.example.movielist.data.mapper

import com.example.movielist.data.remote.dto.MovieDto
import com.example.movielist.domain.model.Movie

fun MovieDto.toDomain(): Movie =
	Movie(
		id          = id,
		title       = title,
		overview    = overview,
		posterUrl   = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
		rating      = voteAverage
	)