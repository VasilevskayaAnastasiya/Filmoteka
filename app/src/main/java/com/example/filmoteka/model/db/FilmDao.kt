package com.example.filmoteka.model.db

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {

    @Upsert
    suspend fun insertToFilm(film: FilmDbItem)
    @Upsert
    suspend fun insertToWishlist(film: FilmWishlistItem)

    @Query("SELECT * FROM films_wishlist")
    fun getWishlist(): Flow<List<FilmWishlistItem>>

    @Query("DELETE FROM films_wishlist WHERE id_api=:filmId")
    suspend fun deletefromWishlist(filmId: String)

    @Query("SELECT f.* FROM films_wishlist fw LEFT JOIN films f ON fw.id_api=f.id_api")
    fun getWishlistFilms(): Flow<List<FilmDbItem>>

    @Upsert
    suspend fun insertToWatchlist(film: FilmWatchlistItem)

    @Query("SELECT * FROM films_watchlist")
    fun getWatchlist(): Flow<List<FilmWatchlistItem>>

    @Query("DELETE FROM films_watchlist WHERE id_api=:filmId")
    suspend fun deletefromWatchlist(filmId: String)

    @Query("SELECT f.* FROM films_watchlist fw LEFT JOIN films f ON fw.id_api=f.id_api")
    fun getWatchlistFilms(): Flow<List<FilmDbItem>>
}