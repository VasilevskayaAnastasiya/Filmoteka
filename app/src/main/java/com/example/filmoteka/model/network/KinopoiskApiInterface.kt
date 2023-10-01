package com.example.filmoteka.model.network

import com.example.filmoteka.di.KINO_API_KEY
import com.example.filmoteka.model.data.FilmModelDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface KinopoiskApiInterface {

    @Headers("X-API-KEY: ${KINO_API_KEY}")
    @GET("/v1.3/movie")
    suspend fun getFilms(@Query("page") page: Int): Response<FilmModelDTO>

    @Headers("X-API-KEY: ${KINO_API_KEY}")
    @GET("/v1.3/movie")
    suspend fun searchByNameFilms(
        @Query("name") name: String,
        @Query("page") page: Int
    ): Response<FilmModelDTO>
}