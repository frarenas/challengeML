package com.farenas.challengeml.di

import android.app.Application
import androidx.room.Room
import com.farenas.challengeml.data.db.AddressDao
import com.farenas.challengeml.data.db.AppDataBase
import com.farenas.challengeml.data.db.AttributeDao
import com.farenas.challengeml.data.db.ProductDao
import com.farenas.challengeml.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDataBase(application: Application): AppDataBase =
        Room.databaseBuilder(application, AppDataBase::class.java, Constants.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideProductDao(appDataBase: AppDataBase): ProductDao = appDataBase.productDao()

    @Provides
    @Singleton
    fun provideAddressDao(appDataBase: AppDataBase): AddressDao = appDataBase.addressDao()

    @Provides
    @Singleton
    fun provideAttributeDao(appDataBase: AppDataBase): AttributeDao = appDataBase.attibuteDao()
}
