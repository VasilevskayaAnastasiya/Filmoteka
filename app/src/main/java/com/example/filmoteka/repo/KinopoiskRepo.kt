package com.example.filmoteka.repo

import com.example.filmoteka.model.data.FilmModelDTO
import retrofit2.Response

interface KinopoiskRepo {

    suspend fun getFilms(): Response<FilmModelDTO>
}