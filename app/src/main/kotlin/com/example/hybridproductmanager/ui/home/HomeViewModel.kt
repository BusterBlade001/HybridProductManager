package com.example.hybridproductmanager.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.hybridproductmanager.domain.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: ProductoRepository) : ViewModel() {

    private val _hasLocalData = MutableStateFlow(false)
    val hasLocalData: StateFlow<Boolean> = _hasLocalData

    init {
        checkLocalData()
    }

    // Hacemos esta función pública para que la Activity pueda llamarla al reanudarse.
    fun checkLocalData() { // <-- CAMBIO DE ACCESO (ANTES ERA private)
        viewModelScope.launch {
            _hasLocalData.value = repository.hasLocalData()
        }
    }

    // Factory para instanciar el ViewModel con el repositorio
    class Factory(private val repository: ProductoRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}