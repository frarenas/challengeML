package com.farenas.challengeml.data.db

import androidx.room.*
import com.farenas.challengeml.data.model.Attribute


@Dao
interface AttributeDao {

    @Transaction
    fun upsert(attributes: List<Attribute>) {
        for (attribute in attributes) {
            if (get(attribute.productId, attribute.name) != null) {
                update(attribute)
            } else {
                insert(attribute)
            }
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(attribute: Attribute)

    @Update
    fun update(attribute: Attribute)

    @Query("SELECT * FROM Attribute WHERE productId = :productId AND name= :name LIMIT 1")
    fun get(productId: String, name: String): Attribute?
}