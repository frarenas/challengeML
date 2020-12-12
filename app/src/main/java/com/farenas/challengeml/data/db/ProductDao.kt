package com.farenas.challengeml.data.db

import androidx.room.*
import com.farenas.challengeml.data.model.Product
import com.farenas.challengeml.data.model.relation.ProductWithAddressAndAttrs
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductDao {
    @Transaction
    fun upsert(product: Product) {
        if (getById(product.id) != null) {
            update(product)
        } else {
            insert(product)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product)

    @Update
    fun update(product: Product)

    @Transaction
    @Query("SELECT * FROM Product ORDER BY lastViewed DESC LIMIT 5")
    fun getProducts(): Flow<List<ProductWithAddressAndAttrs>>

    @Query("SELECT * FROM Product WHERE id = :id LIMIT 1")
    fun getById(id: String): Product?
}