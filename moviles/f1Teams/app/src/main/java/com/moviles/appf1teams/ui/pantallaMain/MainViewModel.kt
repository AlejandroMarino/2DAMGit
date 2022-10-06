package com.moviles.appf1teams.ui.pantallaMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModel(
    stringProvider: StringProvider,
    addTeam: AddTeam,
) : ViewModel() {
    private val _uiState = MutableLiveData<MainState>()
    val uiState: LiveData<MainState> get() = _uiState

    fun errorMostrado() {
        _uiState.value = _uiState.value?.copy(error = null)
    }
}

class MainViewModelFactory(
    private val stringProvider: StringProvider,
    private val addTeam: AddTeam,

    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                stringProvider,
                addTeam,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}