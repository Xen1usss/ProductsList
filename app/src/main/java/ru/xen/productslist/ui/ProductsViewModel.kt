package ru.xen.productslist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.xen.productslist.domain.Product
import ru.xen.productslist.domain.ProductRepository

class ProductsViewModel(private val productRepository: ProductRepository) : ViewModel() {
    companion object {
        const val LIMIT = 20
    }

    enum class Loading {
        TOP, BOTTOM, NONE
    }

    private val _errorState: MutableStateFlow<String?> = MutableStateFlow(null)
    val errorState = _errorState.asStateFlow()

    private val _loadingState: MutableStateFlow<Loading> = MutableStateFlow(Loading.NONE)
    val loadingState = _loadingState.asStateFlow()

    private val _products: MutableStateFlow<List<Product>?> = MutableStateFlow(null)
    val products = _products.asStateFlow()

    init {
        loadProducts(0)
    }

    fun loadProducts(skip: Int = 0) {
        viewModelScope.launch {
            _errorState.emit(null)
            if (skip == 0) {
                _loadingState.emit(Loading.TOP)
            } else {
                _loadingState.emit(Loading.BOTTOM)
            }
            try {
                val data = productRepository.products(skip, LIMIT)

                if (skip == 0) {
                    _products.emit(data)
                } else {
                    _products.emit(_products.value!! + data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _errorState.emit(e.message)
            } finally {
                _loadingState.emit(Loading.NONE)
            }
        }
    }
}
