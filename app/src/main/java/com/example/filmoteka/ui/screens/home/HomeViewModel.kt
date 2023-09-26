package com.example.filmoteka.ui.screens.home

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmoteka.model.data.ErrorDTO
import com.example.filmoteka.repo.KinopoiskRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val retrofit: Retrofit, val kinopoiskRepo: KinopoiskRepo
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    private val _error = Channel<Throwable>(Channel.BUFFERED)
    val error: Flow<Throwable> = _error.consumeAsFlow()

    init {
        viewModelScope.launch {
            val result = kinopoiskRepo.getFilms()

            if (result.isSuccessful) {
                val films = result.body()?.docs?.map { item ->
                    HomeFilm(
                        id = item.id.toString(),
                        title = item.name,
                        year = item.year,
                        poster = Uri.parse(item.poster.url)
                    )
                } ?: emptyList()
                _state.value = HomeState(films = films)
            } else {
                val err = ErrorDTO.parseError(retrofit, result)
                _error.send(RuntimeException(err?.message ?: "Internal error"))
            }
        }
    }
}