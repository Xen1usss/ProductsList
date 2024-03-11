package ru.xen.productslist.domain

data class ProductsListResponse(val products: List<Product>)

data class Product(val title: String, val description: String, val thumbnail: String)