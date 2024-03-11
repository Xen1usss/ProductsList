package ru.xen.productslist.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import ru.xen.productslist.databinding.ProductItemBinding
import ru.xen.productslist.domain.Product

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductHolder>() {

    private val data = mutableListOf<Product>()
    fun setData(dataToSet: List<Product>) {
        data.clear()
        data += dataToSet
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val product = data[position]
        holder.binding.title.text = product.title
        holder.binding.description.text = product.description
        holder.binding.thumbnail.load(product.thumbnail) {
            transformations(CircleCropTransformation())
        }
    }

    class ProductHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)
}