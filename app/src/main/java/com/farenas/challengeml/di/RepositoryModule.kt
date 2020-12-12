package com.farenas.challengeml.di

import com.farenas.challengeml.data.MlApiService
import com.farenas.challengeml.data.db.AddressDao
import com.farenas.challengeml.data.db.AppDataBase
import com.farenas.challengeml.data.db.AttributeDao
import com.farenas.challengeml.data.db.ProductDao
import com.farenas.challengeml.data.model.mapper.ProductMapper
import com.farenas.challengeml.data.repo.ProductRepository
import com.farenas.challengeml.data.repo.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideProductRepository(
        apiService: MlApiService,
        appDataBase: AppDataBase,
        productDao: ProductDao,
        addressDao: AddressDao,
        attributeDao: AttributeDao,
        productMapper: ProductMapper
    ) = ProductRepositoryImpl(
            apiService,
            appDataBase,
            productDao,
            addressDao,
            attributeDao,
            productMapper
        ) as ProductRepository
}
