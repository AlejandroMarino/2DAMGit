package com.example.compose2.ui.screens.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose2.R
import com.example.compose2.domain.modelo.Product
import com.example.compose2.domain.usecases.AddProduct
import com.example.compose2.utils.StringProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val addProduct: AddProduct,
    private val stringProvider: StringProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductState())
    val uiState: StateFlow<ProductState> = _uiState

    fun handleEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.AddProduct -> {
                addProduct(event.product)
            }
            is ProductEvent.OnValueChanged -> {
                onValueChanged(event.product)
            }
        }
    }

    private fun onValueChanged(product: Product) {
        _uiState.value = ProductState(product = product)
    }

    private fun addProduct(product: Product) {
        viewModelScope.launch {
            if (addProduct.invoke(product)) {
                _uiState.value =
                    ProductState(message = stringProvider.getString(R.string.successfully_added))
            } else {
                _uiState.value =
                    ProductState(message = stringProvider.getString(R.string.error_adding))
            }
        }
    }
}