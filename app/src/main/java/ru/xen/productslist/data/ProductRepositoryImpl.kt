package ru.xen.productslist.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.xen.productslist.domain.Product
import ru.xen.productslist.domain.ProductApi
import ru.xen.productslist.domain.ProductRepository

class ProductRepositoryImpl(private val productApi: ProductApi): ProductRepository {
    override suspend fun products(skip: Int, limit: Int): List<Product> {
        return withContext(Dispatchers.IO){
            productApi.products(skip, limit).products
        }
    }
}