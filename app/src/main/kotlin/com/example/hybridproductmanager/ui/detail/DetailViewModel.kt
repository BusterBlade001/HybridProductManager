package com.example.hybridproductmanager.ui.detail

import androidx.lifecycle.ViewModel
import com.example.hybridproductmanager.data.model.Producto

/**
 * ViewModel simple para la Pantalla de Detalle.
 * En una implementación real, podría cargar detalles adicionales o manejar lógica de negocio.
 */
class DetailViewModel : ViewModel() {
    var producto: Producto? = null
}
