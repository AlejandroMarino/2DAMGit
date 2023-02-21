package com.example.composeflows.ui.screens.movieDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeflows.domain.usecases.movies.GetMovie
import com.example.composeflows.utils.NetworkResult
import com.example.composeflows.utils.StringProvider
import com.example.composeflows.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
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
                                )
                            }
                        }
                        else -> _uiState.update {
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