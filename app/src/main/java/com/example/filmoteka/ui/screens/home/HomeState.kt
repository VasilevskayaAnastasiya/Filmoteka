package com.example.filmoteka.ui.screens.home

import android.net.Uri

data class HomeFilm(
    val id: String,
    val poster: Uri?,
    val title: String,
    val year: Int,
)

data class HomeState(
    val films: List<HomeFilm> = emptyList(),
    val lastPage: Int = 0,
    val search: String = "",
    val wishlist: Set<String> = emptySet(),
)
