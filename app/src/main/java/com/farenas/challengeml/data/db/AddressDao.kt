package com.farenas.challengeml.data.db

import androidx.room.*
import com.farenas.challengeml.data.model.Address


@Dao
interface AddressDao {

    @Transaction
    fun upsert(address: Address) {
        if (getById(address.productId) != null) {
            update(address)
        } else {
            insert(address)
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(address: Address)

    @Update
    fun update(address: Address)

    @Query("SELECT * FROM Address WHERE productId = :productId LIMIT 1")
    fun getById(productId: String): Address?
}