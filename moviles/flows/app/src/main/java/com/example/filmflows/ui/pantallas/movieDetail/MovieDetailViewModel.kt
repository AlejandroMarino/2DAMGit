package com.example.filmflows.ui.pantallas.movieDetail

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmflows.R
import com.example.filmflows.domain.modelo.Movie
import com.example.filmflows.domain.usecases.movies.GetMovie
import com.example.filmflows.utils.NetworkResult
import com.example.filmflows.utils.StringProvider
import com.example.filmflows.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val getMovie: GetMovie,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailState())
    val uiState: StateFlow<MovieDetailState> = _uiState

    fun handleEvent(event: MovieDetailEvent) {
        when (event) {
            is MovieDetailEvent.GetMovieDetail -> {
                getMovie(event.id)
            }
        }
    }

    private fun getMovie(id: Int) {
        viewModelScope.launch {
            getMovie.invoke(id).collect { result ->
                if (Utils.hasInternetConnection(stringProvider.context)) {
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    error = result.message,
                                    isLoading = false
                                )
                            }
                        }
                        is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                        is NetworkResult.Success -> _uiState.update {
                            it.copy(
                                movie = result.data, isLoading = false
                            )
                        }
                    }
                } else {
                    when (result) {
                        is NetworkResult.Error -> {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = stringProvider.getString(R.string.noInternet)
                                )
                            }
                        }else -> _uiState.update{
                            it.copy(
                                movie = result.data, isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }
}