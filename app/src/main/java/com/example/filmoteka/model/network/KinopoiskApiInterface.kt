package com.example.filmoteka.model.network

import com.example.filmoteka.di.KINO_API_KEY
import com.example.filmoteka.model.data.FilmModelDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface KinopoiskApiInterface {

    @Headers("X-API-KEY: ${KINO_API_KEY}")
    @GET("/v1.3/movie")
    suspend fun getFilms(): Response<FilmModelDTO>
}