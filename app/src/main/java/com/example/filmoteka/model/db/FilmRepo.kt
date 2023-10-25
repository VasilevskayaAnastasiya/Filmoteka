package com.example.filmoteka.model.db

import com.example.filmoteka.model.FilmItem
import kotlinx.coroutines.flow.Flow

class FilmRepo(val filmDao: FilmDao) {
    suspend fun insertToWishlist(film: FilmItem) {

        filmDao.insertToWishlist(FilmWishlistItem(film.id))

        val filmDb = FilmDbItem(
            film.id, film.title, film.poster.toString(), film.year
        )
        filmDao.insertToFilm(filmDb)
    }

    fun getWishlist(): Flow<List<FilmWishlistItem>> {
        return filmDao.getWishlist()
    }

    suspend fun deletefromWishlist(filmId: String) {
        filmDao.deletefromWishlist(filmId)
    }

    fun getWishlistFilms(): Flow<List<FilmDbItem>>{
        return filmDao.getWishlistFilms()
    }

    suspend fun insertToWatchlist(film: FilmItem) {

        filmDao.insertToWatchlist(FilmWatchlistItem(film.id))

        val filmDb = FilmDbItem(
            film.id, film.title, film.poster.toString(), film.year
        )
        filmDao.insertToFilm(filmDb)
    }

    fun getWatchlist(): Flow<List<FilmWatchlistItem>> {
        return filmDao.getWatchlist()
    }
    suspend fun deletefromWatchlist(filmId: String) {
        filmDao.deletefromWatchlist(filmId)
    }

    fun getWatchlistFilms(): Flow<List<FilmDbItem>>{
        return filmDao.getWatchlistFilms()
    }
}