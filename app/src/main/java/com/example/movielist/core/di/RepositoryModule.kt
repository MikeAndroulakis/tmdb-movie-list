package com.example.movielist.core.di

import com.example.movielist.domain.repository.MovieRepository
import com.example.movielist.data.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Hilt Dependency Injection module responsible for providing
 * the implementation of domain-level repository interfaces.
 *
 * This module defines how Hilt should resolve dependencies
 * whenever a class requires a [MovieRepository].
 *
 * By using @Binds, we tell Hilt:
 *  -> "Whenever a MovieRepository is requested,
 *      provide an instance of MovieRepositoryImpl".
 *
 * The module is installed in the [SingletonComponent],
 * meaning the same instance is shared across the entire app lifecycle.
 */

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
	@Binds
	abstract fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository
}