package com.example.filmoteka.ui.screens.watchlist

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmoteka.model.FilmItem
import com.example.filmoteka.model.db.FilmRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(val filmRepo: FilmRepo): ViewModel() {
    private val _state = MutableStateFlow(WatchListState())
    val state: StateFlow<WatchListState> = _state

    init{
        viewModelScope.launch {
            filmRepo.getWatchlistFilms().collectLatest { items ->
                val films = items.map{ item ->
                    FilmItem(item.id_api, item.poster?.let{Uri.parse(it)}, item.title, item.year)
                }
                _state.update { oldState ->
                    oldState.copy(
                        films = films
                    )
                }
            }
        }
    }
}