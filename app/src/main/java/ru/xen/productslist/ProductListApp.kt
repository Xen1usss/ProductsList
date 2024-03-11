package ru.xen.productslist

import android.app.Application
import org.koin.core.context.startKoin
import ru.xen.productslist.di.domainModule
import ru.xen.productslist.di.networkModule
import ru.xen.productslist.di.viewModelModule

class ProductListApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(viewModelModule, networkModule, domainModule)
        }
    }
}