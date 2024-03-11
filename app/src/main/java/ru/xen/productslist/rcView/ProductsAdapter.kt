package ru.xen.productslist.rcView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.xen.productslist.domain.Product

class ProductsAdapter: RecyclerView.Adapter<ProductsAdapter.ProductHolder> {

    class ProductHolder(item: View): RecyclerView.ViewHolder(item) {
        fun bind(product: Product){

        }
    }
}