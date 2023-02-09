package com.example.compose.ui.screens.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.domain.modelo.Product
import com.example.compose.domain.usecases.AddProduct
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val addProduct: AddProduct,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductState())
    val uiState: StateFlow<ProductState> = _uiState

    fun handleEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.AddProduct -> {
                addProduct(event.product)
            }
        }
    }

    private fun addProduct(product: Product) {
        viewModelScope.launch {
           if ( addProduct.invoke(product)){
               _uiState.value = ProductState(message = "Added successfully")
           } else {
               _uiState.value = ProductState(message = "Error while adding")
           }
        }
    }
}