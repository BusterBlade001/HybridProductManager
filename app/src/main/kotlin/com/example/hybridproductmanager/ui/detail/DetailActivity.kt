package com.example.hybridproductmanager.ui.detail

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.hybridproductmanager.data.model.Producto
import com.example.hybridproductmanager.databinding.ActivityDetailBinding
import com.example.hybridproductmanager.ui.list.ListActivity
import java.text.NumberFormat
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el producto del Intent
        viewModel.producto = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(ListActivity.EXTRA_PRODUCTO, Producto::class.java as Class<java.io.Serializable>) as? Producto
        } else {
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(ListActivity.EXTRA_PRODUCTO) as? Producto
        }

        viewModel.producto?.let { producto ->
            bindProductDetails(producto)
        } ?: finish() // Cerrar si no hay producto
    }

    private fun bindProductDetails(producto: Producto) {
        binding.tvDetailName.text = producto.nombre
        binding.tvDetailSku.text = "SKU: ${producto.sku}"
        binding.tvDetailCategory.text = "Categoría: ${producto.categoria}"
        binding.tvDetailDescription.text = producto.descripcion

        val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
        binding.tvDetailPrice.text = "Precio: ${format.format(producto.precio)}"

        // Detalles de Stock
        binding.tvStockConcepcion.text = "Concepción: ${producto.stockPorSucursal.concepcion} unidades"
        binding.tvStockTalcahuano.text = "Talcahuano: ${producto.stockPorSucursal.talcahuano} unidades"
        binding.tvStockChillan.text = "Chillán: ${producto.stockPorSucursal.chillan} unidades"
        binding.tvStockLosAngeles.text = "Los Ángeles: ${producto.stockPorSucursal.losAngeles} unidades"

        // Nota: La carga de la imagen (binding.ivProductImage) requeriría una librería externa (Glide/Picasso)
        // y se omite aquí por simplicidad del esqueleto de código.
    }
}
