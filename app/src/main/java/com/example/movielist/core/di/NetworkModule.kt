package com.example.movielist.core.di

import com.example.movielist.BuildConfig
import com.example.movielist.data.remote.api.TmdbApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
	    val apiKeyInterceptor = provideApiKeyInterceptor()  // Add the api key
	    val logging = HttpLoggingInterceptor().apply {      // Add logging for debug mode
		    level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
		    else HttpLoggingInterceptor.Level.NONE
	    }


	    return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
		    .addInterceptor(logging)
		    .addInterceptor { chain -> // Retry interceptor
			    val request = chain.request()
			    val response = chain.proceed(request)
			    if ((response.code == 429 || response.code in 500..599)) { // If too many requests error or server error
				    response.close()
				    Thread.sleep(500)
				    return@addInterceptor chain.proceed(request)
			    }
			    response
		    }
	        .connectTimeout(15, TimeUnit.SECONDS)
	        .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

	private fun provideApiKeyInterceptor(): Interceptor = Interceptor { chain ->
		val original = chain.request()
		val newUrl = original.url.newBuilder()
			.addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
			.build()

		val newRequest = original.newBuilder().url(newUrl).build()
		chain.proceed(newRequest)
	}

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()) // Enable support for kotlin data classes
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_BASE_URL) // Set the base url of all api calls(value from gradle.properties)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideTmdbApi(retrofit: Retrofit): TmdbApi =
        retrofit.create(TmdbApi::class.java)
}