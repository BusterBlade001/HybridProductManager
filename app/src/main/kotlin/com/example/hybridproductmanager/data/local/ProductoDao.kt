package com.example.hybridproductmanager.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hybridproductmanager.data.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {

    /**
     * Inserta una lista de productos en la base de datos.
     * Si un producto ya existe (conflicto de clave primaria), lo reemplaza.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(productos: List<Producto>)

    /**
     * Obtiene todos los productos de la base de datos.
     * Retorna un Flow para observar cambios en tiempo real (aunque el requisito solo pide lectura).
     */
    @Query("SELECT * FROM productos")
    fun getAllProductos(): Flow<List<Producto>>

    /**
     * Cuenta el número de productos en la base de datos.
     */
    @Query("SELECT COUNT(sku) FROM productos")
    suspend fun countProductos(): Int

    /**
     * Elimina todos los productos de la base de datos.
     */
    @Query("DELETE FROM productos")
    suspend fun deleteAll()
}
