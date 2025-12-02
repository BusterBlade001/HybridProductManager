package com.example.hybridproductmanager.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Objeto Singleton para configurar y proveer la instancia de Retrofit.
 */
object RetrofitClient {

    // URL base del repositorio de GitHub hasta la carpeta que contiene el JSON
    private const val BASE_URL = "https://raw.githubusercontent.com/chalalo1533/ServicioRest/refs/heads/master/"

    val apiService: ProductoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductoApiService::class.java)
    }
}
