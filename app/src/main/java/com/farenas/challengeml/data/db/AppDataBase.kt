package com.farenas.challengeml.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.farenas.challengeml.data.model.Address
import com.farenas.challengeml.data.model.Attribute
import com.farenas.challengeml.data.model.Product

@Database(
    entities = [
        Product::class,
        Address::class,
        Attribute::class
    ],
    version = 1,
    exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun addressDao(): AddressDao
    abstract fun attibuteDao(): AttributeDao
}