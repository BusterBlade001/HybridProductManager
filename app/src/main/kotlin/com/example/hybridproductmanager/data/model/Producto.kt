package com.example.hybridproductmanager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Clase de datos que representa un Producto.
 * Se utiliza tanto para el parseo del JSON (Retrofit/Gson) como para la entidad de la base de datos local (Room).
 * El campo 'sku' es la clave primaria.
 */
@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey
    @SerializedName("sku") val sku: String,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("categoria") val categoria: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("precio") val precio: Int,
    @SerializedName("imagen") val imagen: String,
    // Nota: El campo 'stock_por_sucursal' es un objeto anidado en el JSON.
    // Para Room, necesitaremos un TypeConverter o aplanarlo, pero para simplificar
    // y cumplir con los requisitos de la lista (SKU, Nombre, Precio), lo manejaremos
    // en el parseo de JSON y lo ignoraremos en la entidad Room si no es estrictamente necesario
    // para la persistencia. Sin embargo, para la Pantalla de Detalle, necesitamos toda la info.
    // Usaremos un TypeConverter para el objeto anidado.
    @SerializedName("stock_por_sucursal") val stockPorSucursal: StockPorSucursal
) : Serializable
