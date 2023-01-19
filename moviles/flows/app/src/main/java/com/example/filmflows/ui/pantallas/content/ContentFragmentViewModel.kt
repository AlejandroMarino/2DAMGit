package com.example.filmflows.ui.pantallas.content

import androidx.lifecycle.ViewModel
import com.example.filmflows.domain.usecases.movies.GetPopularMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ContentFragmentViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
): ViewModel(){

    private val _uiState: MutableStateFlow<ContentFragmentState> by lazy {
        MutableStateFlow((ContentFragmentState()))
    }
    val uiState: StateFlow<ContentFragmentState> = _uiState

    fun handleEvent(event: ContentFragmentEvent) {
        when (event) {
            ContentFragmentEvent.GetPopularMovies -> {
                getPopularMovies()
            }
        }
    }

    private fun getPopularMovies() {

        _uiState. = getPopularMovies.invoke()
    }
}