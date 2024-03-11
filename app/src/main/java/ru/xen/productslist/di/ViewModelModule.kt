package ru.xen.productslist.di

import org.koin.dsl.module
import ru.xen.productslist.ui.ProductsViewModel

val viewModelModule = module {
    single {
        ProductsViewModel(get())
    }
}