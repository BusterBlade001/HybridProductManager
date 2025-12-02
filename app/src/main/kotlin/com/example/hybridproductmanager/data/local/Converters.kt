package com.example.hybridproductmanager.data.local

import androidx.room.TypeConverter
import com.example.hybridproductmanager.data.model.StockPorSucursal
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * TypeConverter para la clase StockPorSucursal, permitiendo a Room almacenar
 * objetos complejos serializándolos a JSON.
 */
class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromStockPorSucursal(stock: StockPorSucursal): String {
        return gson.toJson(stock)
    }

    @TypeConverter
    fun toStockPorSucursal(stockString: String): StockPorSucursal {
        val type = object : TypeToken<StockPorSucursal>() {}.type
        return gson.fromJson(stockString, type)
    }
}
