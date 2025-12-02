package com.example.hybridproductmanager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hybridproductmanager.data.model.Producto

@Database(entities = [Producto::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ProductoDatabase : RoomDatabase() {
    abstract fun productoDao(): ProductoDao

    companion object {
        const val DATABASE_NAME = "producto_db"
    }
}
