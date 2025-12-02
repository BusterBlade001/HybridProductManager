package com.example.hybridproductmanager.di

import android.content.Context
import androidx.room.Room
import com.example.hybridproductmanager.data.local.ProductoDatabase
import com.example.hybridproductmanager.data.remote.RetrofitClient
import com.example.hybridproductmanager.domain.ProductoRepository

/**
 * Contenedor de dependencias simple para la aplicación.
 * En una aplicación real se usaría Hilt o Koin.
 */
class AppContainer(private val context: Context) {

    // 1. Base de Datos Local
    private val database by lazy {
        Room.databaseBuilder(
            context,
            ProductoDatabase::class.java,
            ProductoDatabase.DATABASE_NAME
        )
            .allowMainThreadQueries()
            .build()
    }

    // 2. DAO
    private val productoDao by lazy { database.productoDao() }

    // 3. Servicio API
    private val apiService by lazy { RetrofitClient.apiService }

    // 4. Repositorio
    val repository: ProductoRepository by lazy {
        ProductoRepository(apiService, productoDao)
    }
}
