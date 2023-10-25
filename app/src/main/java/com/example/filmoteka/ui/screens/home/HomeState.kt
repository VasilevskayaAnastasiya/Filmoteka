package com.example.filmoteka.ui.screens.home

import android.net.Uri
import com.example.filmoteka.model.FilmItem

data class HomeState(
    val films: List<FilmItem> = emptyList(),
    val lastPage: Int = 0,
    val search: String = "",
    val wishlist: Set<String> = emptySet(),
    val watchlist: Set<String> = emptySet(),
)
