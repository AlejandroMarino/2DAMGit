package com.example.composeflows.ui.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeflows.R
import com.example.composeflows.domain.usecases.movies.GetPopularMovies
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
class MoviesViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val getPopularMovies: GetPopularMovies,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MoviesState())
    val uiState: StateFlow<MoviesState> = _uiState

    fun handleEvent(event: MoviesEvent) {
        when (event) {
            MoviesEvent.GetPopularMovies -> {
                getPopularMovies()
            }
            MoviesEvent.ErrorCaught -> {
                errorCaught()
            }
        }
    }

    private fun errorCaught() {
        _uiState.update {
            it.copy(
                error = null
            )
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            getPopularMovies.invoke().collect { result ->
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
                                movies = result.data, isLoading = false
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
                        }
                        else -> _uiState.update {
                            it.copy(
                                movies = result.data, isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }
}
