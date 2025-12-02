package com.example.hybridproductmanager.data.remote

import com.example.hybridproductmanager.data.model.Producto
import retrofit2.Response
import retrofit2.http.GET

/**
 * Interfaz de Retrofit para el consumo de la API REST de productos.
 */
interface ProductoApiService {

    @GET("productos.json")
    suspend fun getProductos(): Response<List<Producto>>
}
