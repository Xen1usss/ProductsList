package ru.xen.productslist.di

import org.koin.dsl.module
import ru.xen.productslist.data.ProductRepositoryImpl
import ru.xen.productslist.domain.ProductRepository

val domainModule = module {
    single<ProductRepository> {
        ProductRepositoryImpl(get())
    }
}
