package ru.xen.productslist.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.xen.productslist.domain.Product
import ru.xen.productslist.domain.ProductRepository

class ProductsViewModel(private val productRepository: ProductRepository): ViewModel() {
    companion object {
        const val LIMIT = 10
    }
    private val _products: MutableStateFlow<List<Product>?> = MutableStateFlow(null)
    val products = _products.asStateFlow()
    init {
        loadProducts(0)
    }
    fun loadProducts(skip: Int){
        viewModelScope.launch {
            try {
                val data = productRepository.products(skip, LIMIT)
                _products.emit(data)
            } catch (e: Exception){

            }
            finally {

            }
        }
    }
}
