package com.farenas.challengeml.ui.home

import androidx.lifecycle.*
import com.farenas.challengeml.data.repo.ProductRepository

class HomeViewModelFactory (
    private var productRepository: ProductRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(productRepository) as T
    }
}