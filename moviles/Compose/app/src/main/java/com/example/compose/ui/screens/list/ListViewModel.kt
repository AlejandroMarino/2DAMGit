package com.example.compose.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.domain.usecases.GetProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getProducts: GetProducts,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListState())
    val uiState: StateFlow<ListState> = _uiState

    fun handleEvent(event: ListEvent) {
        when (event) {
            is ListEvent.GetProducts -> {
                getProducts()
            }
        }
    }

    init {
        getProducts()
    }


    private fun getProducts() {
        viewModelScope.launch {
            _uiState.value = ListState(products = getProducts.invoke())
        }
    }
}