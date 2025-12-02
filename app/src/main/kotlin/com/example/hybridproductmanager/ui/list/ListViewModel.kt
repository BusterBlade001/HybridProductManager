package com.example.hybridproductmanager.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.hybridproductmanager.data.model.Producto
import com.example.hybridproductmanager.domain.ProductoRepository
import kotlinx.coroutines.launch

class ListViewModel(private val repository: ProductoRepository) : ViewModel() {

    private val _productos = MutableLiveData<List<Producto>>()
    val productos: LiveData<List<Producto>> = _productos

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message

    // Bandera para saber si los datos actuales provienen de la API o de la BD local
    private val _isDataFromApi = MutableLiveData(false) // <-- CAMBIO: LiveData privado
    val isDataFromApi: LiveData<Boolean> = _isDataFromApi // <-- CAMBIO: LiveData observable

    /**
     * Carga los productos desde la API REST.
     */
    fun loadProductosFromApi() {
        _isLoading.value = true
        _isDataFromApi.value = false // Ocultar antes de empezar
        viewModelScope.launch {
            val result = repository.getProductosFromApi()
            _isLoading.value = false
            if (result != null) {
                _productos.value = result
                _isDataFromApi.value = true // <-- CAMBIO: Se actualiza el LiveData
                _message.value = "Productos cargados desde la API REST."
            } else {
                _productos.value = emptyList()
                _isDataFromApi.value = false // <-- CAMBIO: Se actualiza el LiveData
                _message.value = "Error al cargar productos desde la API. Verifique su conexión."
            }
        }
    }

    /**
     * Carga los productos desde la base de datos local.
     */
    fun loadProductosFromLocalDb() {
        _isLoading.value = true
        _isDataFromApi.value = false // Ocultar antes de empezar
        viewModelScope.launch {
            repository.getProductosLocal().collect { list ->
                _productos.value = list
                _isLoading.value = false
                _isDataFromApi.value = false // <-- CAMBIO: Se actualiza el LiveData
                _message.value = "Productos cargados desde la Base de Datos Local."
            }
        }
    }

    /**
     * Guarda los productos actualmente visibles en la base de datos local.
     */
    fun saveCurrentProductsToLocalDb() {
        val currentProducts = _productos.value
        if (currentProducts.isNullOrEmpty()) {
            _message.value = "No hay productos para guardar."
            return
        }

        viewModelScope.launch {
            repository.saveProductosLocal(currentProducts)
            _message.value = "¡Productos guardados exitosamente en la Base de Datos Local!"
        }
    }

    // Factory para instanciar el ViewModel con el repositorio
    class Factory(private val repository: ProductoRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}