package com.example.filmoteka.repo

import com.example.filmoteka.model.data.FilmModelDTO
import com.example.filmoteka.model.network.KinopoiskApiInterface
import retrofit2.Response

class KinopoiskRepoImpl(val kinopoiskApi: KinopoiskApiInterface) : KinopoiskRepo {

    override suspend fun getFilms(page: Int): Response<FilmModelDTO> = kinopoiskApi.getFilms(page)
    override suspend fun searchByNameFilms(name: String, page: Int): Response<FilmModelDTO> =
        kinopoiskApi.searchByNameFilms(name, page)
}