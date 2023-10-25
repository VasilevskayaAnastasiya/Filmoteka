package com.example.filmoteka.ui.screens.watchlist

import com.example.filmoteka.model.FilmItem

data class WatchListState (
    val films: List<FilmItem> = emptyList(),
)