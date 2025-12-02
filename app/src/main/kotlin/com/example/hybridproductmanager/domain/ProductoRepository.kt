package com.example.hybridproductmanager.domain

import com.example.hybridproductmanager.data.local.ProductoDao
import com.example.hybridproductmanager.data.model.Producto
import com.example.hybridproductmanager.data.remote.ProductoApiService
import kotlinx.coroutines.flow.Flow

/**
 * Repositorio que actúa como fuente única de verdad para los datos de Producto.
 * Coordina entre la fuente de datos remota (API) y la fuente de datos local (Room).
 */
class ProductoRepository(
    private val apiService: ProductoApiService,
    private val productoDao: ProductoDao
) {

    /**
     * Obtiene la lista de productos desde la API REST.
     * @return Lista de Producto o null en caso de error.
     */
    suspend fun getProductosFromApi(): List<Producto>? {
        return try {
            val response = apiService.getProductos()
            if (response.isSuccessful) {
                response.body()
            } else {
                // Manejo de error de respuesta HTTP (ej. 404, 500)
                null
            }
        } catch (e: Exception) {
            // Manejo de error de conexión (ej. Sin internet)
            null
        }
    }

    /**
     * Guarda una lista de productos en la base de datos local.
     */
    suspend fun saveProductosLocal(productos: List<Producto>) {
        productoDao.insertAll(productos)
    }

    /**
     * Obtiene la lista de productos desde la base de datos local.
     * Retorna un Flow para la lectura.
     */
    fun getProductosLocal(): Flow<List<Producto>> {
        return productoDao.getAllProductos()
    }

    /**
     * Verifica si hay datos almacenados localmente.
     */
    suspend fun hasLocalData(): Boolean {
        return productoDao.countProductos() > 0
    }
}
