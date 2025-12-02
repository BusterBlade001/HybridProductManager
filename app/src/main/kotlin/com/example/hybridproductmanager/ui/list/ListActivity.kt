package com.example.hybridproductmanager.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.hybridproductmanager.data.model.Producto
import com.example.hybridproductmanager.databinding.ActivityListBinding
import com.example.hybridproductmanager.di.AppContainer
import com.example.hybridproductmanager.ui.detail.DetailActivity
import com.example.hybridproductmanager.HybridProductManagerApp


class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private lateinit var appContainer: AppContainer
    private lateinit var adapter: ProductoAdapter

    private val viewModel: ListViewModel by viewModels {
        ListViewModel.Factory(appContainer.repository)
    }

    companion object {
        const val EXTRA_LOAD_FROM_API = "load_from_api"
        const val EXTRA_PRODUCTO = "producto_seleccionado"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = (applicationContext as HybridProductManagerApp).appContainer
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupListeners()
        setupObservers()
        loadData()
    }

    private fun setupRecyclerView() {
        adapter = ProductoAdapter { producto ->
            navigateToDetail(producto)
        }
        binding.rvProductos.adapter = adapter
    }

    private fun setupListeners() {
        binding.btnSaveLocal.setOnClickListener {
            viewModel.saveCurrentProductsToLocalDb()
        }
    }

    private fun setupObservers() {
        viewModel.productos.observe(this) { productos ->
            adapter.submitList(productos)
            // Lógica de visibilidad del botón de guardar ELIMINADA de aquí.
        }

        // <-- CAMBIO: Nuevo observador que gestiona la visibilidad del botón
        viewModel.isDataFromApi.observe(this) { isFromApi ->
            binding.btnSaveLocal.visibility = if (isFromApi) View.VISIBLE else View.GONE
        }
        // CAMBIO -->

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.message.observe(this) { message ->
            message?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun loadData() {
        val loadFromApi = intent.getBooleanExtra(EXTRA_LOAD_FROM_API, true)
        if (loadFromApi) {
            viewModel.loadProductosFromApi()
        } else {
            viewModel.loadProductosFromLocalDb()
        }
    }

    private fun navigateToDetail(producto: Producto) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(EXTRA_PRODUCTO, producto)
        }
        startActivity(intent)
    }
}