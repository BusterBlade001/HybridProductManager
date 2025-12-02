package com.example.hybridproductmanager.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.hybridproductmanager.databinding.ActivityHomeBinding
import com.example.hybridproductmanager.di.AppContainer
import com.example.hybridproductmanager.ui.list.ListActivity
import com.example.hybridproductmanager.utils.NetworkUtils
import kotlinx.coroutines.launch
import com.example.hybridproductmanager.HybridProductManagerApp


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var appContainer: AppContainer

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.Factory(appContainer.repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = (applicationContext as HybridProductManagerApp).appContainer // Asumiendo que AppContainer se inicializa en Application
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.btnLoadApi.setOnClickListener {
            if (NetworkUtils.isInternetAvailable(this)) {
                // Navegar a ListActivity y cargar desde API
                val intent = Intent(this, ListActivity::class.java).apply {
                    putExtra(ListActivity.EXTRA_LOAD_FROM_API, true)
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Sin conexión a Internet", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnLoadLocal.setOnClickListener {
            lifecycleScope.launch {
                // El ViewModel ya verificó si hay datos, pero lo hacemos de nuevo para el mensaje
                if (viewModel.hasLocalData.value) {
                    // Navegar a ListActivity y cargar desde BD Local
                    val intent = Intent(this@HomeActivity, ListActivity::class.java).apply {
                        putExtra(ListActivity.EXTRA_LOAD_FROM_API, false)
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(this@HomeActivity, "No hay datos locales almacenados", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewModel.hasLocalData.collect { hasData ->
                binding.tvLocalDataStatus.text = if (hasData) {
                    "Estado: Hay datos locales almacenados."
                } else {
                    "Estado: Base de datos local vacía."
                }
            }
        }
    }
}
