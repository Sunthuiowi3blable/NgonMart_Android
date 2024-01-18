package me.amitshekhar.mvvm.ui.topheadline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import me.amitshekhar.mvvm.data.model.Product
import me.amitshekhar.mvvm.data.repository.ProductRepository
import me.amitshekhar.mvvm.ui.base.UiState

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<Product>>> = _uiState
    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            productRepository.getProducts()
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }
                .collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}