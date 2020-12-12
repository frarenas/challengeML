package com.farenas.challengeml.di

import com.farenas.challengeml.data.repo.FakeAndroidProductRepository
import com.farenas.challengeml.data.repo.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModuleTest {

    @Singleton
    @Provides
    @Named("test_repo")
    fun provideProductRepository() =
        FakeAndroidProductRepository() as ProductRepository
}
