package com.example.filmoteka.model.db

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
data class FilmDbItem(
    @PrimaryKey val id_api: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "poster") val poster: String?,
    @ColumnInfo(name = "year") val year: Int
)

@Entity(tableName = "films_wishlist")
data class FilmWishlistItem(
    @PrimaryKey val id_api: String
)

@Entity(tableName = "films_watchlist")
data class FilmWatchlistItem(
    @PrimaryKey val id_api: String
)