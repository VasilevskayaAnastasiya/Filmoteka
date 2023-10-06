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
}