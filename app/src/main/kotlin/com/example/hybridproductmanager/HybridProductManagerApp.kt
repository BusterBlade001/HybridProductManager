package com.example.hybridproductmanager

import android.app.Application
import com.example.hybridproductmanager.di.AppContainer

class HybridProductManagerApp : Application() {

    // Contenedor de dependencias de la aplicación
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}
