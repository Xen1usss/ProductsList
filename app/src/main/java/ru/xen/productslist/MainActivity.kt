package ru.xen.productslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.xen.productslist.ui.ProductsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, ProductsFragment())
                .commitNow()
        }
    }
}