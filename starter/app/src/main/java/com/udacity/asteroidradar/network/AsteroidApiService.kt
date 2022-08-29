package com.udacity.asteroidradar.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.DailyPicture
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = Constants.BASE_URL
private const val API_KEY = Constants.API_KEY

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface AsteroidsApiService {
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("start_date")
        startDate: String?,
        @Query("end_date")
        endDate: String?,
        @Query("api_key")
        key: String = API_KEY
    ): Any

    @GET("planetary/apod")
    suspend fun getDailyPictureData(
        @Query("api_key")
        key: String = API_KEY
    ): DailyPicture
}

object AsteroidsApi {
    val retrofitService: AsteroidsApiService by lazy { retrofit.create(AsteroidsApiService::class.java) }
}
