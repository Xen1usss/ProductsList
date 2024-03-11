package ru.xen.productslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import ru.xen.productslist.domain.ProductApi
import ru.xen.productslist.ui.ProductsFragment

class MainActivity : AppCompatActivity() {

    // val test: ProductApi by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.container, ProductsFragment())
            .commitNow()
//        lifecycle.coroutineScope.launch{
//            withContext(Dispatchers.IO){
//                val a = test.products(0,10)
//                println(a)
//            }
//        }
    }
}