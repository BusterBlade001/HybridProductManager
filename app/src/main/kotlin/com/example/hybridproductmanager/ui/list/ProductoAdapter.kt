package com.example.hybridproductmanager.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hybridproductmanager.data.model.Producto
import com.example.hybridproductmanager.databinding.ItemProductoBinding
import java.text.NumberFormat
import java.util.Locale

class ProductoAdapter(private val onClick: (Producto) -> Unit) :
    ListAdapter<Producto, ProductoAdapter.ProductoViewHolder>(ProductoDiffCallback) {

    class ProductoViewHolder(private val binding: ItemProductoBinding, private val onClick: (Producto) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        private var currentProducto: Producto? = null

        init {
            itemView.setOnClickListener {
                currentProducto?.let {
                    onClick(it)
                }
            }
        }

        fun bind(producto: Producto) {
            currentProducto = producto
            binding.tvSku.text = producto.sku
            binding.tvName.text = producto.nombre

            // Formatear el precio a moneda local (ejemplo con CLP)
            val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
            binding.tvPrice.text = format.format(producto.precio)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductoViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object ProductoDiffCallback : DiffUtil.ItemCallback<Producto>() {
    override fun areItemsTheSame(oldItem: Producto, newItem: Producto): Boolean {
        return oldItem.sku == newItem.sku
    }

    override fun areContentsTheSame(oldItem: Producto, newItem: Producto): Boolean {
        return oldItem == newItem
    }
}
