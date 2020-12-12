package com.farenas.challengeml.ui.searchresult

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.farenas.challengeml.data.repo.ProductRepository
import com.farenas.challengeml.utils.NetworkUtils

class SearchResultViewModelFactory(
    private val productRepository: ProductRepository,
    private val networkUtils: NetworkUtils,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return SearchResultViewModel(productRepository, networkUtils, handle) as T
    }
}