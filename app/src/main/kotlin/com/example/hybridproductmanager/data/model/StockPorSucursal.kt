package com.example.hybridproductmanager.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Clase de datos para el stock por sucursal, anidada dentro de Producto.
 * Se utiliza para el parseo del JSON.
 */
data class StockPorSucursal(
    @SerializedName("Concepción") val concepcion: Int,
    @SerializedName("Talcahuano") val talcahuano: Int,
    @SerializedName("Chillán") val chillan: Int,
    @SerializedName("Los Ángeles") val losAngeles: Int
) : Serializable
