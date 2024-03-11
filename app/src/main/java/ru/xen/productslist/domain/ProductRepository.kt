package ru.xen.productslist.domain

interface ProductRepository {
    suspend fun products(skip: Int, limit: Int): List<Product>

}