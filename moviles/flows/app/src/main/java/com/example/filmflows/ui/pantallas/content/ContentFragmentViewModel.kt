package com.example.filmflows.ui.pantallas.content

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmflows.data.repository.MoviesRepository
import com.example.filmflows.domain.usecases.movies.GetPopularMovies
import com.example.filmflows.utils.NetworkResult
import com.example.filmflows.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContentFragmentViewModel @Inject constructor(
    @ApplicationContext val appContext: Context,
    private val getPopularMovies: GetPopularMovies,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ContentFragmentState())
    val uiState: StateFlow<ContentFragmentState> = _uiState

    fun handleEvent(event: ContentFragmentEvent) {
        when (event) {
            ContentFragmentEvent.GetPopularMovies -> {
                getPopularMovies()
            }
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            if (Utils.hasInternetConnection(appContext)) {
                getPopularMovies.invoke().collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _uiState.update {
                                    it.copy(
                                        error = result.message,
                                        isLoading = false
                                    )
                                }
                                //_uiError.send(result.message ?: "Error")
                            }
                            is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
                            is NetworkResult.Success -> _uiState.update {
                                it.copy(
                                    movies = result.data, isLoading = false
                                )
                            }
                        }
                    }
            } else {
                _uiState.update {
                    it.copy(
                        error = "no hay internet cargando de cache.",
                        isLoading = false,
                    )
                }
            }
        }
    }
}