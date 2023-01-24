package com.example.filmflows.ui.pantallas.movies

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmflows.R
import com.example.filmflows.domain.usecases.movies.GetPopularMovies
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
                                )
                            }
                            Toast.makeText(stringProvider.context, stringProvider.getString(R.string.noInternet), Toast.LENGTH_SHORT).show()
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
